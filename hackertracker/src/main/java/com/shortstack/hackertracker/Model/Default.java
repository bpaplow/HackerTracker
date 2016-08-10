package com.shortstack.hackertracker.Model;

import android.content.Context;

import com.shortstack.hackertracker.R;
import com.shortstack.hackertracker.Utils.SharedPreferencesUtil;

import org.parceler.Parcel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Whitney Champion
 * Date: 7/11/13
 * Time: 9:20 AM
 * Description:
 */

@Parcel
public class Default implements Serializable {

    private int id;
    private String type;
    private String date;
    private String title;
    private String who;
    private String description;
    private String begin;
    private String end;
    private String location;
    private String link;
    private Integer starred;
    private String image;
    private Integer is_new;
    private Integer demo;
    private Integer tool;
    private Integer exploit;

    public Integer getStarred() {
        return starred;
    }

    public void setStarred(Integer starred) {
        this.starred = starred;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String who) {
        this.who = who;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description.replace("\\\"", "\"");
    }

    public String getName() {
        return who;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isNew() {
        return is_new != null && is_new == 1;
    }

    public void setIsNew(Integer is_new) {
        this.is_new = is_new;
    }

    public Integer getDemo() {
        return demo;
    }

    public void setDemo(Integer demo) {
        this.demo = demo;
    }

    public Integer getTool() {
        return tool;
    }

    public void setTool(Integer tool) {
        this.tool = tool;
    }

    public Integer getExploit() {
        return exploit;
    }

    public void setExploit(Integer exploit) {
        this.exploit = exploit;
    }

    public Default() {

    }

    public Default(int id, String title, String type, String description, String location, String who, String end, String begin, String link, String image, int demo, int tool, int exploit, int starred, int is_new){
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.who = who;
        this.location = location;
        this.begin = begin;
        this.end = end;
        this.link = link;
        this.image = image;
        this.exploit = exploit;
        this.demo = demo;
        this.tool = tool;
        this.starred = starred;
        this.is_new = is_new;
    }

    public boolean isTool() {
        return getTool() == 1;
    }

    public boolean isExploit() {
        return getExploit() == 1;
    }

    public boolean isDemo() {
        return getDemo() == 1;
    }

    public boolean isStarred() {
        return getStarred() == 1;
    }

    public String getTimeStamp( Context context ) {
        // No start time, return TBA.
        if( getBegin().equals("") )
            return context.getResources().getString(R.string.tba);

        String time = "";

        if (SharedPreferencesUtil.shouldShowMilitaryTime()) {
            time = getBegin();

        } else {
            String dateStr = getBegin();
            DateFormat readFormat = new SimpleDateFormat("HH:mm");
            DateFormat writeFormat = new SimpleDateFormat("h:mm aa");
            Date date = null;
            try {
                date = readFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date != null) {
                time = writeFormat.format(date);
            }
        }

        return time;
    }
}
