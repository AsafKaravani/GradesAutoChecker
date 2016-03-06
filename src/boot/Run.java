package boot;

import java.util.List;

import org.openqa.selenium.WebElement;

import model.InfoNetHandle;

public class Run {

	public static void main(String[] args) {
		InfoNetHandle h = new InfoNetHandle("208189449","88Dqidsycij");
		h.login();
		List<WebElement> links = h.getMenuLinks();
		for (WebElement link : links) {
			System.out.println("link name: \"" + link.getText() + "\"");
		}

	}

}
