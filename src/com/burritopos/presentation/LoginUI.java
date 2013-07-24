package com.burritopos.presentation;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
//import java.math.BigInteger;
//import java.security.SecureRandom;
import javax.swing.*;
import org.apache.log4j.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.bouncycastle.crypto.*;
//import org.bouncycastle.crypto.generators.*;
//import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
//import org.bouncycastle.jce.provider.symmetric.AES;
//import org.bouncycastle.jce.provider.symmetric.DESede.KeyGenerator;

//import com.burritopos.business.Encryptor;
import com.burritopos.domain.Employee;
import com.burritopos.service.net.AuthenticationSvcSocketImpl;
import com.burritopos.service.net.IAuthenticationSvc;

/**
 *
 * @author james.bloom
 */
public class LoginUI extends JFrame  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger dLog = Logger.getLogger(LoginUI.class);
	private JLabel userLbl = new JLabel("Username: ");
	private JLabel passLbl = new JLabel("Password: ");
	private JTextField userTxt = new JTextField("");
	private JPasswordField passTxt = new JPasswordField(10);
	private JButton submitBtn = new JButton("Login");
	private JButton cancelBtn = new JButton("Exit");
	private IAuthenticationSvc authSvc;
	//private AsymmetricCipherKeyPair keyPair;

	// Spring configuration
    private static final String SPRING_CONFIG_DEFAULT = "applicationContext.xml";
	
	public LoginUI() {
		super("Neato Burrito Login");

        //Spring Framework IoC
        ClassPathXmlApplicationContext beanfactory = null;
        try {
            beanfactory = new ClassPathXmlApplicationContext(SPRING_CONFIG_DEFAULT);
            authSvc = (AuthenticationSvcSocketImpl)beanfactory.getBean("authSvc");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (beanfactory != null) {
                beanfactory.close();
            }
        }

		submitBtn.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event)
					{submitBtnOnClick();}
				}
			);

		cancelBtn.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event)
					{cancelBtnOnClick();}
				}
			);

		Container container = getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		Container tContainer = new Container();
		tContainer.setLayout(new FlowLayout());
		userTxt.setColumns(15);
		tContainer.add(userLbl);
		tContainer.add(userTxt);
		container.add(tContainer, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		tContainer = new Container();
		tContainer.setLayout(new FlowLayout());
		passTxt.setColumns(15);
		tContainer.add(passLbl);
		tContainer.add(passTxt);
		container.add(tContainer, c);

		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		tContainer = new Container();
		tContainer.setLayout(new FlowLayout());
		tContainer.add(submitBtn);
		tContainer.add(cancelBtn);
		container.add(tContainer, c);

	}

	private void submitBtnOnClick() {
		dLog.trace("Login submit button has been clicked");

		try {
			publicKeyCryptoTest();

			if(!userTxt.getText().equals("") && !passTxt.getPassword().toString().equals("")){
				dLog.trace("User/Pass are good");
				Employee e = new Employee(userTxt.getText(),userTxt.getText(),1);

				if(authSvc.login(e, String.valueOf(passTxt.getPassword()))) {
					dLog.trace("User authenticated; launching Neato Burrito App");
					dispose();

					MainUI mainUI = new MainUI();
					mainUI.setBounds(0, 0, 750, 750);
					mainUI.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(LoginUI.this, "Invalid Credentials.", "Error", JOptionPane.OK_OPTION);
				}
			}
			else {
				JOptionPane.showMessageDialog(LoginUI.this, "Please enter a username and password.", "Information", JOptionPane.OK_OPTION);
			}
		}
		catch(Exception e) {
			dLog.error("Error");
			e.printStackTrace();
		}
	}
	
	private void publicKeyCryptoTest() {
		// TODO: finish playing around with public key transfers
		/*RSAKeyPairGenerator rsaKeyPairGen = new RSAKeyPairGenerator();
		rsaKeyPairGen.init(new RSAKeyGenerationParameters (
		        new BigInteger("10001", 16),//publicExponent
		        SecureRandom.getInstance("SHA1PRNG"),//prng
		        2048,//strength
		        80//certainty
		    ));
		keyPair = rsaKeyPairGen.generateKeyPair();

		String symKey = Encryptor.generateRandomKey();
		dLog.trace("Symmetric Key: " + symKey);
		Encryptor cipher = new Encryptor(symKey);
        String testStr = "It worked";
        dLog.trace("cleartext: " + testStr);
        byte[] cipherText = cipher.encryptString(testStr);
        testStr = new String(cipherText);
        dLog.trace("encrypted: " + testStr);
        testStr = cipher.decryptString(cipherText);
        dLog.trace("decrypted: " + testStr);*/
	}

	private void cancelBtnOnClick() {
		dLog.trace("Login cancel button has been clicked");

		dLog.trace("Closing Order Creation Form");
		dispose();
	}
}
