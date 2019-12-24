package com.cinema.client.entities;

import java.util.ArrayList;

public class Hall {

    private String sector;
    private int row;
    private int col;

    private ArrayList<Cell> booked;
    private ArrayList<Cell> free;
    private ArrayList<Cell> disabled;
    private ArrayList<Cell> bought;

    public Hall(String sector, int row, int col, ArrayList<Cell> booked, ArrayList<Cell> free, ArrayList<Cell> disabled, ArrayList<Cell> bought) {
        this.sector = sector;
        this.row = row;
        this.col = col;
        this.booked = booked;
        this.free = free;
        this.disabled = disabled;
        this.bought = bought;
    }

    public ArrayList<Cell> getDisabled() {
        return disabled;
    }

    public ArrayList<Cell> getBought() {
        return bought;
    }

    public void setBought(ArrayList<Cell> bought) {
        this.bought = bought;
    }

    public void setDisabled(ArrayList<Cell> disabled) {
        this.disabled = disabled;
    }

    public ArrayList<Cell> getFree() {
        return free;
    }

    public void setFree(ArrayList<Cell> free) {
        this.free = free;
    }

    public ArrayList<Cell> getBooked() {
        return booked;
    }

    public void setBooked(ArrayList<Cell> booked) {
        this.booked = booked;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public class Cell{

        private int row;
        private int col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }
    }

}
