package com.levelup.recruit;

import com.levelup.recruit.domain.enumeration.Company;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("dev")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class SeleniumTest {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    @Test
    void line_test() {
        System.setProperty("webdriver.chrome.driver", "C:/Task/tools/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://careers.linecorp.com/ko/jobs?ca=Engineering&ci=Bundang,Seoul");
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.job_list > li"));
        for (WebElement element : elements) {
            String title = element.findElement(By.cssSelector("a h3.title")).getText();

            String jobNoticeUri = element.findElement(By.cssSelector("a")).getAttribute("href");
            String jobNoticePath = jobNoticeUri.substring(jobNoticeUri.lastIndexOf("/"));
            String url = Company.LINE.getUrl() + jobNoticePath;

            String noticeEndDate = element.findElement(By.cssSelector("a span.date")).getText();

            System.out.println("title: " + title);
            System.out.println("url: " + url);
            System.out.println("noticeEndDate: " + noticeEndDate);
        }

        driver.quit();
    }

    @Test
    void bamin_test() {
        System.setProperty("webdriver.chrome.driver", "C:/Task/tools/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://career.woowahan.com/?keyword=&category=jobGroupCodes%3ABA005001#recruit-list");
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.recruit-type-list > li"));
        for (WebElement element : elements) {
            System.out.println(element.getText());
        }

        driver.quit();
    }

    @Test
    void coupang_test() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.coupang.jobs/kr/jobs?page=1&search=Software+Engineer&location=Seoul%2c+South+Korea&pagesize=20#results");
        List<WebElement> elements = driver.findElements(By.cssSelector("div.job-listing > div.card-job"));
        for (WebElement element : elements) {
            String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
            final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link"))
                            .getAttribute("href");
            final String noticeEndDate = "채용 마감시";
            System.out.println("title: " + title);
            System.out.println("url: " + url);
            System.out.println("noticeEndDate: " + noticeEndDate);
        }

        driver.quit();
    }

    @Test
    void naver_test() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();

        String params = "?subJobCdArr=1010001%2C1010002%2C1010003%2C1010004%2C1010005%2C1010006%2C1010007%2C1010008%2C1010020%2C1020001%2C1030001%2C1030002%2C1040001%2C1060001&sysCompanyCdArr=KR%2CNB%2CWM%2CSN%2CNL%2CWTKR%2CNFN%2CNI&empTypeCdArr=&entTypeCdArr=&workAreaCdArr=&sw=&subJobCdData=1010001&subJobCdData=1010002&subJobCdData=1010003&subJobCdData=1010004&subJobCdData=1010005&subJobCdData=1010006&subJobCdData=1010007&subJobCdData=1010008&subJobCdData=1010020&subJobCdData=1020001&subJobCdData=1030001&subJobCdData=1030002&subJobCdData=1040001&subJobCdData=1060001&sysCompanyCdData=KR&sysCompanyCdData=NB&sysCompanyCdData=WM&sysCompanyCdData=SN&sysCompanyCdData=NL&sysCompanyCdData=WTKR&sysCompanyCdData=NFN&sysCompanyCdData=NI";
        driver.get(Company.NAVER.getUrl() + params);
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.card_list > li"));
        int prevSize = elements.size();
        for (int i = 0; i < 10; i++) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

            // Wait for more items to load
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            elements = driver.findElements(By.cssSelector("ul.card_list > li"));
            int curSize = elements.size();

            if (prevSize == curSize) {
                break;
            }

            prevSize = curSize;
        }

        elements.forEach(jobElement -> {
            String title = jobElement.findElement(By.cssSelector("a.card_link > h4.card_title")).getText();

            String htmlOnClickAttrValue = jobElement.findElement(By.cssSelector("a.card_link")).getAttribute("onclick");
            System.out.println("htmlOnClickAttrValue: " + htmlOnClickAttrValue);

            String jobNoticeKey;
            if (htmlOnClickAttrValue.contains("'")) {
                jobNoticeKey = htmlOnClickAttrValue.substring(htmlOnClickAttrValue.indexOf("'") + 1, htmlOnClickAttrValue.lastIndexOf("'"));
            } else {
                jobNoticeKey = htmlOnClickAttrValue.substring(htmlOnClickAttrValue.indexOf("(") + 1, htmlOnClickAttrValue.lastIndexOf(")"));
            }

            String url = "https://recruit.navercorp.com/rcrt/view.do?annoId=" + jobNoticeKey;

            String noticeEndDate = jobElement.findElement(By.cssSelector("dl.card_info dd.info_text:last-child")).getText();

            System.out.println("title : " + title);
            System.out.println("url : " + url);
            System.out.println("noticeEndDate : " + noticeEndDate);
        });

        driver.quit();
    }
}
