package com.gosemathraj.railsofindia.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iamsparsh on 14/2/17.
 */

public class LiveTrainStatus {

    private List<Route> route;
    private String start_date;
    private CurrentStation current_station;
    private Integer response_code;
    private String error;
    private String position;
    private String train_number;

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public CurrentStation getCurrent_station() {
        return current_station;
    }

    public void setCurrent_station(CurrentStation current_station) {
        this.current_station = current_station;
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTrain_number() {
        return train_number;
    }

    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }

    public class CurrentStation {

        private String actdep;
        private String actarr_date;
        private String scharr_date;
        private Integer day;
        private Integer distance;
        private Boolean has_arrived;
        private Boolean has_departed;
        private Integer no;
        private String station;
        private Station station_;
        private String actarr;
        private String status;
        private String schdep;
        private Integer latemin;

        public String getActdep() {
            return actdep;
        }

        public void setActdep(String actdep) {
            this.actdep = actdep;
        }

        public String getActarr_date() {
            return actarr_date;
        }

        public void setActarr_date(String actarr_date) {
            this.actarr_date = actarr_date;
        }

        public String getScharr_date() {
            return scharr_date;
        }

        public void setScharr_date(String scharr_date) {
            this.scharr_date = scharr_date;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Boolean getHas_arrived() {
            return has_arrived;
        }

        public void setHas_arrived(Boolean has_arrived) {
            this.has_arrived = has_arrived;
        }

        public Boolean getHas_departed() {
            return has_departed;
        }

        public void setHas_departed(Boolean has_departed) {
            this.has_departed = has_departed;
        }

        public Integer getNo() {
            return no;
        }

        public void setNo(Integer no) {
            this.no = no;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public Station getStation_() {
            return station_;
        }

        public void setStation_(Station station_) {
            this.station_ = station_;
        }

        public String getActarr() {
            return actarr;
        }

        public void setActarr(String actarr) {
            this.actarr = actarr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSchdep() {
            return schdep;
        }

        public void setSchdep(String schdep) {
            this.schdep = schdep;
        }

        public Integer getLatemin() {
            return latemin;
        }

        public void setLatemin(Integer latemin) {
            this.latemin = latemin;
        }
    }

    public class Route {

        private String actdep;
        private String actarr_date;
        private String scharr_date;
        private Integer day;
        private Integer distance;
        private Boolean has_arrived;
        private Boolean has_departed;
        private Integer no;
        private String station;
        private Station station_;
        private String actarr;
        private String status;
        private String schdep;
        private Integer latemin;

        public String getActdep() {
            return actdep;
        }

        public void setActdep(String actdep) {
            this.actdep = actdep;
        }

        public String getActarr_date() {
            return actarr_date;
        }

        public void setActarr_date(String actarr_date) {
            this.actarr_date = actarr_date;
        }

        public String getScharr_date() {
            return scharr_date;
        }

        public void setScharr_date(String scharr_date) {
            this.scharr_date = scharr_date;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Boolean getHas_arrived() {
            return has_arrived;
        }

        public void setHas_arrived(Boolean has_arrived) {
            this.has_arrived = has_arrived;
        }

        public Boolean getHas_departed() {
            return has_departed;
        }

        public void setHas_departed(Boolean has_departed) {
            this.has_departed = has_departed;
        }

        public Integer getNo() {
            return no;
        }

        public void setNo(Integer no) {
            this.no = no;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public Station getStation_() {
            return station_;
        }

        public void setStation_(Station station_) {
            this.station_ = station_;
        }

        public String getActarr() {
            return actarr;
        }

        public void setActarr(String actarr) {
            this.actarr = actarr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSchdep() {
            return schdep;
        }

        public void setSchdep(String schdep) {
            this.schdep = schdep;
        }

        public Integer getLatemin() {
            return latemin;
        }

        public void setLatemin(Integer latemin) {
            this.latemin = latemin;
        }
    }

    public class Station {

        private String name;
        private String code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
