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
    private SimpleDateFormat mTimeFormat;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mTimeFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public String getFormatDate() {
        return mDateFormat.format(mDate);
    }

    public String getFormatTime() {
        return mTimeFormat.format(mDate);
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
