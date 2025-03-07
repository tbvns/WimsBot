import lombok.AllArgsConstructor;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;

public class Scrapper {
    public static ChromeDriver driver;

    public static void setup() {
        driver = new ChromeDriver();
        driver.get("https://wims.univ-cotedazur.fr/wims/wims.cgi?lang=fr&+module=adm%2Fclass%2Fclasses&+type=authparticipant&+class=3299396&+subclass=yes");

        new Thread(() -> {
            while (true) {
                String url = driver.getCurrentUrl();
                if (url.contains("module=home")) {
                    main.mainFrame.dispose();
                    showList();
                    break;
                } else if (url.contains("module=adm%2Fsheet")) {
                    findExercise();
                }
            }
        }){{
            setName("Scarper Thread");
            setDaemon(true);
            start();
        }};
    }

    public static void showList() {
        JFrame jFrame = new JFrame("I GOT GOOD SHIT");
        jFrame.setSize(600, 600);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(60, 60, 60, 60));
        jFrame.setContentPane(panel);

        List<WebElement> elements = driver.findElements(By.cssSelector(".wims_user_sheet_title a"));
        for (WebElement element : elements) {
            panel.add(new JButton(element.getText()){{
                addActionListener(a -> {
                    element.click();
                    jFrame.dispose();
                });
            }});
        }

        jFrame.setVisible(true);
    }

    @Data
    @AllArgsConstructor
    public static class sheet {
        private String url;
        private int current;
        private int max;
    }

    public static List<sheet> findExercise() {
        List<sheet> sheets = new ArrayList<>();

        List<WebElement> list = driver.findElements(By.cssSelector(".wims_sheet_list .wims_exo_item"));
        for (WebElement element : list) {
            String url = element.findElement(By.cssSelector("a")).getDomProperty("href");
            WebElement text = element.findElement(By.cssSelector(".wims_sheet_score.small"));
            int min = getMinMax(text.getText())[0];
            int max = getMinMax(text.getText())[1];

            sheets.add(new sheet(url, min, max));
        }

        return sheets;
    }

    public static int[] getMinMax(String data) {
        int current = Integer.parseInt(data.split(":")[1].split("/")[0].trim());
        int max = Integer.parseInt(data.split("/")[1].split(" ")[0].trim());

        return new int[]{current, max};
    }

    public static void doExercises(List<sheet> exs) {
        for (sheet ex : exs) {
            driver.get(ex.url);

        }
    }
}
