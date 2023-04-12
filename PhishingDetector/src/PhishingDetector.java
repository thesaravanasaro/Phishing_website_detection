//public class PhishingDetector {
//}

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PhishingDetector extends JFrame implements ActionListener {
    private JLabel urlLabel, resultLabel;
    private JTextField urlTextField;
    private JButton checkButton;

    public PhishingDetector() {
        // set up GUI components
        urlLabel = new JLabel("Enter URL: ");
        urlTextField = new JTextField(20);
        checkButton = new JButton("Check");
        resultLabel = new JLabel("");

        // set up action listener
        checkButton.addActionListener(this);

        // set up layout
        setLayout(new FlowLayout());

        // add components to container
        add(urlLabel);
        add(urlTextField);
        add(checkButton);
        add(resultLabel);

        // set window properties
        setTitle("Web Phishing Detector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String url = urlTextField.getText();
        boolean isPhishing = checkPhishing(url);
        if (isPhishing) {
            resultLabel.setText("This URL is a phishing site.");
            resultLabel.setForeground(Color.RED);
        } else {
            resultLabel.setText("This URL is safe.");
            resultLabel.setForeground(Color.GREEN);
        }
    }

    private boolean checkPhishing(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements inputs = doc.getElementsByTag("input");
            for (Element input : inputs) {
                if (input.attr("type").equalsIgnoreCase("password")) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new PhishingDetector();
    }
}

