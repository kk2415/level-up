package com.levelup.channel.domain.service;

import com.levelup.channel.domain.entity.*;
import com.levelup.channel.domain.model.ChannelActivityScore;
import com.levelup.channel.domain.model.Stats;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChannelActivityScoreService {

    private final ChannelRepository channelRepository;

    @Transactional
    public List<ChannelActivityScore> findWeeklyActivityScoresTop10() {
        List<ChannelActivityScore> weeklyActivityScores = findWeeklyActivityScores();
        weeklyActivityScores.sort(Comparator.comparing(ChannelActivityScore::getaScore).reversed());

        return weeklyActivityScores;
    }

    @Transactional
    public List<ChannelActivityScore> findWeeklyActivityScores() {
        return channelRepository.findAll().stream()
                .map(channel -> findWeeklyActivityScore(channel.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ChannelActivityScore findWeeklyActivityScore(Long channelId) {
        List<LocalDateTime> weeklyDataSet = findWeeklyDataSet(channelId);

        List<Long> dailyFrequencies = findDailyFrequency(weeklyDataSet);
        for (int i = 7 - dailyFrequencies.size(); i > 0; i-- ) {
            dailyFrequencies.add(0L);
        }

        Stats stats = findActivityStats(dailyFrequencies);
        return ChannelActivityScore.of(
                channelId,
                stats,
                findActivityScore(stats)
        );
    }

    private double findActivityScore(Stats stats) {
        double mean = stats.getMean();
        double standardDeviation = stats.getStandardDeviation();
        double aScore;

        if (standardDeviation == 0) {
            aScore = mean;
        } else {
            aScore = (mean / standardDeviation) + mean;
        }

        return aScore;
    }

    private Stats findActivityStats(List<Long> dailyFrequencies) {
        SummaryStatistics stats = new SummaryStatistics();
        for (Long frequency : dailyFrequencies) {
            stats.addValue(frequency);
        }

        double mean = stats.getMean();
        double standardDeviation = stats.getStandardDeviation();
        double aScore;

        return Stats.from(mean, standardDeviation);
    }

    private List<Long> findDailyFrequency(List<LocalDateTime> dataSet) {
        LocalDateTime min = dataSet.stream().min(LocalDateTime::compareTo).orElse(LocalDateTime.now());

        return new ArrayList<>(dataSet.stream()
                .collect(Collectors.groupingBy(dateTime -> ChronoUnit.DAYS.between(min, dateTime), Collectors.counting()))
                .values());
    }

    //TODO:: DB 요청 횟수 최소화. (현재 조회 쿼리가 많이 발생함)
    private List<LocalDateTime> findWeeklyDataSet(Long channelId) {
        final Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        LocalDate mondayDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime mondayDateTime = LocalDateTime.of(mondayDate, LocalTime.MIN);

        LocalDate sundayDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime sundayDateTime = LocalDateTime.of(sundayDate, LocalTime.MAX);

        List<LocalDateTime> memberWeeklyCreationTimes = channel.getChannelMembers().stream()
                .filter(member -> member.getCreatedAt().isAfter(mondayDateTime) && member.getCreatedAt().isBefore(sundayDateTime))
                .map(ChannelMember::getCreatedAt)
                .collect(Collectors.toList());

        List<ChannelArticle> channelArticles = channel.getChannelArticles();
        List<LocalDateTime> articleWeeklyCreationTimes =  channelArticles.stream()
                .filter(article -> article.getCreatedAt().isAfter(mondayDateTime) && article.getCreatedAt().isBefore(sundayDateTime))
                .map(ChannelArticle::getCreatedAt)
                .collect(Collectors.toList());

        List<LocalDateTime> commentWeeklyCreationTimes =  channelArticles.stream()
                .map(ChannelArticle::getComments)
                .flatMap(Collection::stream)
                .filter(comment -> comment.getCreatedAt().isAfter(mondayDateTime) && comment.getCreatedAt().isBefore(sundayDateTime))
                .map(ChannelComment::getCreatedAt)
                .collect(Collectors.toList());

        List<LocalDateTime> voteWeeklyCreationTimes = channelArticles.stream()
                .map(ChannelArticle::getVotes)
                .flatMap(Collection::stream)
                .filter(vote -> vote.getCreatedAt().isAfter(mondayDateTime) && vote.getCreatedAt().isBefore(sundayDateTime))
                .map(ChannelArticleVote::getCreatedAt)
                .collect(Collectors.toList());

        List<LocalDateTime> dataSet = new ArrayList<>();
        dataSet.addAll(memberWeeklyCreationTimes);
        dataSet.addAll(articleWeeklyCreationTimes);
        dataSet.addAll(commentWeeklyCreationTimes);
        dataSet.addAll(voteWeeklyCreationTimes);

        return dataSet;
    }
}
