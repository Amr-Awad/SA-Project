public class Rating
{

    private double Rate;
    private double RatingSum;
    private int NumberOfRatings;

    public Rating()
    {
        Rate = 0;
        RatingSum = 0;
        NumberOfRatings = 0;
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
}
