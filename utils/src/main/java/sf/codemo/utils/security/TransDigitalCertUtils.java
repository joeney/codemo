package sf.codemo.utils.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.operator.ContentSigner;

public abstract class TransDigitalCertUtils {
	private static final String ISSUER = "CN=Codemo Root CA, O=Codemo Corporation, OU=CA Center, L=HongKong, ST=HongKong, C=CN";
	private static final String SUBJECT_SUFFIX = "O=Codemo Corporation, OU=CA Center, L=HongKong, ST=HongKong, C=CN";
	private static final int KEY_SIZE = 1024;
	private static final long VALID_PERIOD_SECONDS = 1L * 2 * 365 * 24 * 60 * 60;//two years
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	public static TransCert generateClientCert(String userId) {
		TransCert tc = new TransCert();
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			keygen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keypair = keygen.generateKeyPair();

			X509Certificate cert = generateClientX509Certificate(keypair, userId);

			tc.setDigitalCertificates(cert);
			PrivateKey priKey = keypair.getPrivate();
			PublicKey pubKey = keypair.getPublic();
			tc.setPrivateKey(toHexString(priKey.getEncoded()));
			tc.setPublicKey(toHexString(pubKey.getEncoded()));
		} catch (Exception e) {
			throw new RuntimeException("generate X509Certificate error!", e);
		}
		return tc;
	}

	private static String toHexString(byte[] b) {
		if (null == b || 0 == b.length) return "";
		StringBuffer result = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			result.append(hex.toLowerCase());
		}
		return result.toString();
	}

	private static byte[] hex2byte(String str) {
		if (str == null) return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1) return null;
		byte[] b = new byte[len / 2];
		for (int i = 0; i < str.length(); i += 2) {
			b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
		}
		return b;
	}

	private static X509Certificate generateClientX509Certificate(KeyPair keypair, String userId) {
		X509Certificate cert = null;
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(hex2byte(RootCert.PRIVATE_KEY));
			PrivateKey priKey = kf.generatePrivate(ks);

			X500Name issuer = new X500Name(ISSUER);
			X500Name subject = new X500Name("CN=Codemo Client [" + userId + "], " + SUBJECT_SUFFIX);
			BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
			Date notBefore = new Date(System.currentTimeMillis() - 1L * 24 * 60 * 60 * 1000);
			Date notAfter = new Date(System.currentTimeMillis() + VALID_PERIOD_SECONDS * 1000L);
			SubjectPublicKeyInfo pki = SubjectPublicKeyInfo.getInstance(ASN1Sequence.getInstance(keypair.getPublic().getEncoded()));
			X509v3CertificateBuilder builder = new X509v3CertificateBuilder(issuer, serial, notBefore, notAfter, subject, pki);

			final byte[] signatureData;
			Signature signature = Signature.getInstance("SHA256WithRSAEncryption");
			signature.initSign(priKey);
			signature.update(keypair.getPublic().getEncoded());
			signatureData = signature.sign();
			X509CertificateHolder holder = builder.build(new ContentSigner() {
				ByteArrayOutputStream buf = new ByteArrayOutputStream();

				@Override
				public byte[] getSignature() {
					try {
						buf.write(signatureData);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					return signatureData;
				}

				@Override
				public OutputStream getOutputStream() {
					return buf;
				}

				@Override
				public AlgorithmIdentifier getAlgorithmIdentifier() {
					return AlgorithmIdentifier.getInstance(PKCSObjectIdentifiers.sha256WithRSAEncryption);
				}
			});
			cert = new JcaX509CertificateConverter().setProvider("BC").getCertificate(holder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cert;
	}

	public abstract class RootCert {
		public static final String PRIVATE_KEY = "30820275020100300d06092a864886f70d01010105000482025f3082025b0201000281810099a065cacfe34baab66c5d4de1e7b3762e05eeacdf5f695e5cabdb23c780cbb5ac5e7019e102d6d417f811a9baa4ac6b15ea9b2f098011706fa9fc5a160086555a540dee08606c4ecdecabc10a661a85b13faa6cfd7adbc50e28d081feeb7f3b6640a66c6a7b58df61f602a4b0d85e8487594ab3dfeb7d6cc122a24c74afe39102030100010281807319d7ea62637bf7c581df4e50e9566e3f0136d7d2ec1b78159ab52f53744e2c299f000283b1aadf71aded1debb7216f82162a7acd75649b872e26e22005f89e9d1989735ce885de1845bb52b5a5b1db5bbe9056acf6cc582d0a799550852c945517a0b1d21c9d048b2b3773ab5f494796d2b67f723001c74aad7003e54e2ad9024100faa6fe985f8281677d05145f698a38d00b9de8fdbd58c1398a3a34d20edafcec0b41763645613f50f4e89b06e820ebc3b7cbe3f417fb66830da20a550c5cd89b0241009ce7787a81fbf1b042615fa285cc4ba529def698c68b242762ad147500942875f2d171eaa85e5f29ad3bc587b9dfa459e49d266c269421ad7088d931484d49430240043ae13d0d0f94c37c56a39b609a968edc4353f54446c52c056b2a0d097724a18f2711a9a4ce1955726ca2812155bce4d6ab38339c5bffc4e26c03a49fcac95502406a027e450f43a134fc747bbffbf45efb95819e4cf480180a40423e7b1acbff56499bb702db84ff78d2217b414b954d9eaab67eba3d8a820283f10205a91b06af0240381b72f7a4512d22844a226eb54cffe9b39171b8f3afb20f9b88a269a588efbd303aca0e9ee97667e0f8c7004aad4f78734ec63b1738f0d8b67b7c65fcf63454";
		public static final String PUBLIC_KEY = "30819f300d06092a864886f70d010101050003818d003081890281810099a065cacfe34baab66c5d4de1e7b3762e05eeacdf5f695e5cabdb23c780cbb5ac5e7019e102d6d417f811a9baa4ac6b15ea9b2f098011706fa9fc5a160086555a540dee08606c4ecdecabc10a661a85b13faa6cfd7adbc50e28d081feeb7f3b6640a66c6a7b58df61f602a4b0d85e8487594ab3dfeb7d6cc122a24c74afe3910203010001";
		public static final String ISSUER = "CN=Codemo Root CA, O=Codemo Corporation, OU=CA Center, L=HongKong, ST=HongKong, C=CN";
		public static final String SUBJECT = "CN=Codemo Root CA, O=Codemo Corporation, OU=CA Center, L=HongKong, ST=HongKong, C=CN";
	}

	public static class TransCert {
		private String privateKey;
		private String publicKey;
		private Certificate digitalCertificates;

		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}

		public Certificate getDigitalCertificates() {
			return digitalCertificates;
		}

		public String getDigitalCertificatesText() {
			String s = null;
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				PEMWriter output = new PEMWriter(new OutputStreamWriter(bos));
				output.writeObject(digitalCertificates);
				output.close();
				byte[] bytes = bos.toByteArray();
				s = asciiToString(bytes);
				bos.close();
			} catch (Exception e) {
				throw new RuntimeException("generate digital certificates text error!", e);
			}
			return s;
		}

		public static String asciiToString(byte[] bytes) {
			StringBuilder builder = new StringBuilder(1024);
			for (int i = 0; i < bytes.length; i++) {
				String text = "" + bytes[i];
				for (int j = 0; j < text.length(); j++) {
					if (text.charAt(j) <= '2' && j <= text.length() - 3) {
						int code = Integer.parseInt(text.substring(j, j + 3));
						builder.append((char) code);
						j += 2;
					} else {
						int code = Integer.parseInt(text.substring(j, j + 2));
						builder.append((char) code);
						j += 1;
					}
				}
			}
			return builder.toString();
		}

		public void setDigitalCertificates(Certificate digitalCertificates) {
			this.digitalCertificates = digitalCertificates;
		}

	}
}
