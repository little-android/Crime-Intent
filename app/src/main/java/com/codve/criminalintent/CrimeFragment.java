package com.codve.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static android.widget.CompoundButton.*;

public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField; // 输入框
    private Button mDateButton; // 日期按钮
    private Button mTimeButton; // 时间按钮
    private Button mDeleteButton; // 删除按钮
    private CheckBox mSolvedCheckBox; // 单选框

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    // 创建 fragment 实例, 并传入 argument
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity())
                .updateCrime(mCrime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after
            ) {

            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count
            ) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());
                // 设置日期对话框 fragment 的目标为 crimeFragment
                // 匿名实例不能直接使用 this.
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = v.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(mCrime.getDate());
                // 设置时间对话框 fragment 的模板为 crimeFragment
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mDeleteButton = v.findViewById(R.id.crime_delete);
        mDeleteButton.setText(R.string.crime_delete_label);
        mDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CrimeLab.get(getActivity()).deleteCrime(mCrime);
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.finish();
            }
        });

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        return v;
    }

    // 获取数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
            updateTime();
        }
        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            Calendar calendar = Calendar.getInstance();
            // 获取旧的年月日
            calendar.setTime(mCrime.getDate());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // 获取新的时分
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);

            date = new GregorianCalendar(year, month, day, hour, minute).getTime();
            mCrime.setDate(date);
            updateDate();
            updateTime();
        }
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getFormatDate());
    }

    private void updateTime() {
        mTimeButton.setText(mCrime.getFormatTime());
    }

}
