package com.hudawei.dateandtimesample;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        Chronometer.OnChronometerTickListener,
        DatePicker.OnDateChangedListener,
        TimePicker.OnTimeChangedListener,
        CalendarView.OnDateChangeListener,
        NumberPicker.OnValueChangeListener
{

    private Chronometer chronometer;
    private String time;
    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFormat24Time();
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(this);

        datePicker =(DatePicker)findViewById(R.id.datePicker);
        initDatePicker();

        timePicker=(TimePicker)findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(this);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);

        NumberPicker numberPicker= (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(5);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(this);

    }

    private void initDatePicker() {
        Calendar calendar=Calendar.getInstance();
        //初始化设置  年月日，以及日期改变时的回调
        datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),this);
    }

    /**
     * 获取24进制模式下的时间字符串
     */
    private void getFormat24Time() {
        TextClock textClock=(TextClock)findViewById(R.id.textClock);
        //是不是24进制模式
        boolean flag24=textClock.is24HourModeEnabled();
        String time=null;
        //如果是，getFormat24Hour获取时间
        if(flag24){
            time=textClock.getFormat24Hour().toString();
        }else{//如果不是，getFormat12Hour获取12进制模式的事件
            time=textClock.getFormat12Hour().toString();
        }
        //如果都不能获取，取TextColock里面的默认24小时制的时间
        if(time==null)
            time=textClock.DEFAULT_FORMAT_24_HOUR.toString();
    }

    public void start(View view){
        //计时器开始
        chronometer.start();
    }

    public void stop(View view){
        //计时器结束
        chronometer.stop();
    }

    /**
     *
     * System.currentTimeMillis()它不适合用在需要时间间隔的地方，
     * 如Thread.sleep, Object.wait等，因为可以通过System.setCurrentTimeMillis来改变它的值。
     *
     * SystemClock.currentThreadTimeMillis(); // 在当前线程中已运行的时间
     SystemClock.elapsedRealtime(); // 从开机到现在的毫秒书（手机睡眠(sleep)的时间也包括在内）
     SystemClock.uptimeMillis(); // 从开机到现在的毫秒书（手机睡眠的时间不包括在内）
     SystemClock.sleep(100); // 类似Thread.sleep(100);但是该方法会忽略InterruptedException
     SystemClock.setCurrentTimeMillis(1000); // 设置时钟的时间，和System.setCurrentTimeMillis类似

     // 时间间隔
     long timeInterval = SystemClock.uptimeMillis() - lastTime;
     // do something with timeInterval
     * @param view
     */
    public void reset(View view){
        //设置基准点时间，SystemClock.elapsedRealtime()为从第一次开机到现在的时间
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    public void format(View view){
        //设置显示时间的格式，固定
        chronometer.setFormat("Time:s%");
    }

    /**
     * OnDateChangedListener的回调方法
     * @param chronometer
     */
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        time=chronometer.getText().toString();
        if(time.equals("00:10")){
            Toast.makeText(this,"10秒",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * DatePicker.OnDateChangedListener回调方法
     * @param datePicker
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        Toast.makeText(this,year+"/"+month+"/"+day,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
        Toast.makeText(this,hour+":"+minute,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
        Toast.makeText(this,year+":"+month+":"+day,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        Toast.makeText(this,"oldVal:"+oldVal+"\tnewVal:"+newVal,Toast.LENGTH_SHORT).show();
    }
}
