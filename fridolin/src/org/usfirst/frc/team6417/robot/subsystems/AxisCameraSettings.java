package org.usfirst.frc.team6417.robot.subsystems;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.usfirst.frc.team6417.robot.Util;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AxisCameraSettings extends Subsystem {

	protected String scheme = "http";
	protected String host = "10.64.17.6";
	protected String path = "/axis-cgi/param.cgi";
	protected String username = "root";
	protected String password = "6417";
	
	public AxisCameraSettings() {
		
	}
	
	public AxisCameraSettings(String scheme, String host, String path, String username, String password) {
		setScheme(scheme);
		setHost(host);
		setPath(path);
		setUsername(username);
		setPassword(password);
	}
	
	public AxisCameraSettings setScheme(String scheme) {
		// TODO check for valid scheme
		this.scheme = scheme;
		return this;
	}
	
	public AxisCameraSettings setHost(String host) {
		this.host = host;
		return this;
	}
	
	public AxisCameraSettings setPath(String path) {
		this.path = path;
		return this;
	}
	
	public AxisCameraSettings setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public AxisCameraSettings setPassword(String password) {
		this.password = password;
		return this;
	}
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected String buildBaseUrl() {
    	return scheme + "://" + host + path;
    }
    
    protected URLConnection addBasicAuth(URLConnection url) {
		String basicAuth = "Basic " + Base64.getUrlEncoder().encodeToString((username+":"+password).getBytes());
    	url.setRequestProperty("Authorization", basicAuth);
    	return url;
    }
    
    @SuppressWarnings("unused") 
    public boolean setExposure(int exposure) throws MalformedURLException, IOException {
    	exposure = Util.limit(exposure, 0, 100);
    	String url = buildBaseUrl() + "?action=update&root.ImageSource.I0.Sensor.ExposureValue=" + exposure;
    	URLConnection conn = addBasicAuth(new URL(url).openConnection());
    	InputStream is = conn.getInputStream();
    	// TODO check for HTTP return code
    	return true;
    }

    @SuppressWarnings("unused") 
    public boolean setBrightness(int brightness) throws MalformedURLException, IOException {
    	brightness = Util.limit(brightness, 0, 100);
    	String url = buildBaseUrl() + "?action=update&root.ImageSource.I0.Sensor.Brightness=" + brightness;
    	URLConnection conn = addBasicAuth(new URL(url).openConnection());
    	InputStream is = conn.getInputStream();
    	// TODO check for HTTP return code
    	return true;    	
    }
}

