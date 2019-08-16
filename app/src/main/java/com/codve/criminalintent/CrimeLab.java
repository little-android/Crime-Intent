package com.codve.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab; // 单例

    private List<Crime> mCrimes; // 数据库

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    // 私有构造方法
    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

        // 生成 100 个 crime
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID uuid) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(uuid)) {
                return crime;
            }
        }
        return null;
    }
}
