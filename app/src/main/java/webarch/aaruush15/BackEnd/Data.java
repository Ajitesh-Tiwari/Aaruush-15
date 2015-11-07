package webarch.aaruush15.BackEnd;

import android.os.Bundle;

public class Data {

    //private variables
    int _id=0;
    String _name="";
    String _type="";
    String _domain="";
    String _venue="";
    String _time="";
    String _date="";
    String _desc="";
    String _image="";


    // Empty constructor
    public Data(){

    }
    // constructor
    public Data(int id, String name, String type,String domain,String venue,String time,String date,String desc,String image){
        this._id = id;
        this._name = name;
        this._type = type;
        this._domain=domain;
        this._venue=venue;
        this._time=time;
        this._date=date;
        this._desc=desc;
        this._image=image;

    }

    // constructor
    public Data(String name, String type,String domain,String venue,String time,String date,String desc,String image){
        this._name = name;
        this._type = type;
        this._domain=domain;
        this._venue=venue;
        this._time=time;
        this._date=date;
        this._desc=desc;
        this._image=image;
    }
    public Bundle getAsBundle()
    {
        Bundle bundle=new Bundle();
        bundle.putInt("id",this._id);
        bundle.putString("name",this._name);
        bundle.putString("type",this._type);
        bundle.putString("domain",this._domain);
        bundle.putString("venue",this._venue);
        bundle.putString("time",this._time);
        bundle.putString("image", this._image);
        bundle.putString("desc", this._desc);
        bundle.putString("date",this._date);

        return bundle;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }
    // getting name
    public String getType(){
        return this._type;
    }

    // setting name
    public void setType(String type){
        this._type = type;
    }
    // getting name
    public String getDomain(){
        return this._domain;
    }

    // setting name
    public void setDomain(String domain){
        this._domain = domain;
    }
    // getting name
    public String getVenue(){
        return this._venue;
    }

    // setting name
    public void setVenue(String venue){
        this._venue = venue;
    }
    // getting name
    public String getTime(){
        return this._time;
    }

    // setting name
    public void setTime(String time){
        this._time = time;
    }
    // getting name
    public String getDate(){
        return this._date;
    }

    // setting name
    public void setDate(String date){
        this._date = date;
    }
    // getting name
    public String getDesc(){
        return this._desc;
    }

    // setting name
    public void setDesc(String desc){
        this._desc = desc;
    }

    public String getImage(){
        return this._image;
    }

    // setting name
    public void setImage(String image){
        this._image = image;
    }


}