package sf.codemo.utils.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import sf.codemo.utils.security.TransDigitalCertUtils.TransCert;

public class TestTransDigitalCertUtils {
	private final Log logger = LogFactory.getLog(getClass());

	@Test
	public void main() {
		TransCert cert = TransDigitalCertUtils.generateClientCert("Andy");
		logger.info("\n" + cert.getDigitalCertificatesText());
	}
}
