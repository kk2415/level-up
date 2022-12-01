package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JobFilterCondition {

    private Company company;
    private OpenStatus openStatus;

    public static JobFilterCondition of(Company company, OpenStatus openStatus) {
        return new JobFilterCondition(company, openStatus);
    }
}
