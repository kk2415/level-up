package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.LineJob;
import com.levelup.recruit.domain.enumeration.Company;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LineScraper {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    private JsoupTemplate jsoupTemplate;

    public LineScraper(@Qualifier("LineConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<Job> findJobs() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();

        String params = "?ca=Engineering&ci=Bundang,Seoul";
        driver.get(Company.LINE.getUrl() + params);
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.job_list > li"));

        List<Job> jobs = elements.stream().map(element -> {
            String title = element.findElement(By.cssSelector("a h3.title")).getText();

            String jobNoticeUri = element.findElement(By.cssSelector("a")).getAttribute("href");
            String jobNoticePath = jobNoticeUri.substring(jobNoticeUri.lastIndexOf("/"));
            String url = Company.LINE.getUrl() + jobNoticePath;

            String noticeEndDate = element.findElement(By.cssSelector("a span.date")).getText();
            return LineJob.of(title, url, noticeEndDate);
        }).collect(Collectors.toList());

        driver.quit();

        return jobs;
    }
}
