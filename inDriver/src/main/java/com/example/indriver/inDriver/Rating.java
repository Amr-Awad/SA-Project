package com.example.indriver.inDriver;

public class Rating
{
    private int RateID;


    private double Rate;
    private double RatingSum;
    private int NumberOfRatings;

    public Rating()
    {
        Rate = 0;
        RatingSum = 0;
        NumberOfRatings = 0;
    }

    public void setRateID(int rateID) {
        RateID = rateID;
    }

    public int getRateID() {
        return RateID;
    }

    public double getRate()
    {

        return Rate;
    }

    public int getNumberOfRatings()
    {

        return NumberOfRatings;
    }

    public void AddRate(double Rate)
    {
        RatingSum += Rate;
        NumberOfRatings++;
        this.Rate = RatingSum/NumberOfRatings;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public void setRatingSum(double ratingSum) {
        RatingSum = ratingSum;
    }

    public double getRatingSum() {
        return RatingSum;
    }


    public void setNumberOfRatings(int numberOfRatings) {
        NumberOfRatings = numberOfRatings;
    }
}
