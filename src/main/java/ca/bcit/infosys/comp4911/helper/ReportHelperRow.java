package ca.bcit.infosys.comp4911.helper;

/**
 * A project manager needs to see the most recent WPs associated with a project, and how much work has been
 * done for each work package within the project.
 * Created by craigleclair on 3/14/2014.
 */
public class ReportHelperRow {
    private double P1;
    private double P2;
    private double P3;
    private double P4;
    private double P5;
    private double DS;
    private double SS;
    private double total;
    private double labourDollars;
    private String wpNumber;
    private String wpDescription;

    public ReportHelperRow(String wpNumber) {
        this.wpNumber = wpNumber;
        P1 = 0;
        P2 = 0;
        P3 = 0;
        P4 = 0;
        P5 = 0;
        DS = 0;
        SS = 0;
    }

    public void incrementP1(double hours){
        P1 += hours;
    }

    public void incrementP2(double hours){
        P2 += hours;
    }

    public void incrementP3(double hours){
        P3 += hours;
    }

    public void incrementP4(double hours){
        P4 += hours;
    }

    public void incrementP5(double hours){
        P5 += hours;
    }

    public void incrementSS(double hours){
        SS += hours;
    }

    public void incrementDS(double hours){
        DS += hours;
    }

    public double getP1() {
        return P1;
    }

    public void setP1(double p1) {
        P1 = p1;
    }

    public double getP2() {
        return P2;
    }

    public void setP2(double p2) {
        P2 = p2;
    }

    public double getP4() {
        return P4;
    }

    public void setP4(double p4) {
        P4 = p4;
    }

    public double getP3() {
        return P3;
    }

    public void setP3(double p3) {
        P3 = p3;
    }

    public double getP5() {
        return P5;
    }

    public void setP5(double p5) {
        P5 = p5;
    }

    public double getDS() {
        return DS;
    }

    public void setDS(double DS) {
        this.DS = DS;
    }

    public double getSS() {
        return SS;
    }

    public void setSS(double SS) {
        this.SS = SS;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getLabourDollars() {
        return labourDollars;
    }

    public void setLabourDollars(double labourDollars) {
        this.labourDollars = labourDollars;
    }

    public String getWpNumber() {
        return wpNumber;
    }

    public void setWpNumber(String wpNumber) {
        this.wpNumber = wpNumber;
    }

    public String getWpDescription() {
        return wpDescription;
    }

    public void setWpDescription(String wpDescription) {
        this.wpDescription = wpDescription;
    }
}
