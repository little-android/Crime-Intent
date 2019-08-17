package com.codve.criminalintent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;

    private Date mDate;
    private boolean mSolved;
    private SimpleDateFormat mDateFormat;

    public Crime() {
        this(UUID.randomUUID()); // 相当于调用 Crime(UUID id)
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public String getFormatDate() {
        return mDateFormat.format(mDate);
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
