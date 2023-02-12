package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.domain.domain.CoupangJob;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.enumeration.Company;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoupangScraper {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    private final JsoupTemplate jsoupTemplate;

    public CoupangScraper(@Qualifier("CoupangConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<Job> findJobs() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();

        int page = 1;
        int lastPage = 5;
        String params;

        List<Job> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            params = "?search=Software+engineer+backend+frontend&location=Seoul%2C+South+Korea&location=South+Korea&pagesize=20#results&page=" + page;
            driver.get(Company.COUPANG.getUrl() + params);

            List<WebElement> elements = driver.findElements(By.cssSelector("div.job-listing > div.card-job"));
            List<CoupangJob> scrapedJobs = elements.stream().map((element) -> {
                final String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
                final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getAttribute("href");
                final String noticeEndDate = "채용 마감시";

                return CoupangJob.of(title, url, noticeEndDate);
            }).collect(Collectors.toList());

            jobs.addAll(scrapedJobs);
        }

        return jobs;
    }
}
