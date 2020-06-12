package com.archi.architecture.lottery;

import java.util.List;

public class BigLottoBall {
    private int Period_Id;//期号
    private int RedBall_One;//红球1
    private int RedBall_Tow;//红球2
    private int RedBall_Three;//红球3
    private int RedBall_Four;//红球4
    private int RedBall_Fives;//红球5
    private int BlueBall_One;//蓝球1
    private int BlueBall_Tow;//蓝球2
    private long Prize_Pool_Bonus;//奖池奖金
    private int First_Prize_Number;//一等奖注数
    private int First_Prize_Bonus;//一等奖奖金
    private int Second_Prize_Number;//二等奖注数
    private int Second_Prize_Bonus;//二等奖奖金
    private int Total_Bet_Amount;//总投注额
    private String Lottery_Date;//开奖日期

    public BigLottoBall(List<String> infoData) {
        if (infoData.size()==15){
            this.Period_Id=Integer.parseInt(infoData.get(0));
            this.RedBall_One=Integer.parseInt(infoData.get(1));
            this.RedBall_Tow=Integer.parseInt(infoData.get(2));
            this.RedBall_Three=Integer.parseInt(infoData.get(3));
            this.RedBall_Four=Integer.parseInt(infoData.get(4));
            this.RedBall_Fives=Integer.parseInt(infoData.get(5));
            this.BlueBall_One=Integer.parseInt(infoData.get(6));
            this.BlueBall_Tow=Integer.parseInt(infoData.get(7));
            this.Prize_Pool_Bonus=Long.parseLong(infoData.get(8));
            this.First_Prize_Number=Integer.parseInt(infoData.get(9));
            this.First_Prize_Bonus=Integer.parseInt(infoData.get(10));
            this.Second_Prize_Number=Integer.parseInt(infoData.get(11));
            this.Second_Prize_Bonus=Integer.parseInt(infoData.get(12));
            this.Total_Bet_Amount=Integer.parseInt(infoData.get(13));
            this.Lottery_Date=infoData.get(14);
        }
    }
    public BigLottoBall() {}

    @Override
    public String toString() {
        return "BigLottoBall{" +
                "Period_Id=" + Period_Id +
                ", RedBall_One=" + RedBall_One +
                ", RedBall_Tow=" + RedBall_Tow +
                ", RedBall_Three=" + RedBall_Three +
                ", RedBall_Four=" + RedBall_Four +
                ", RedBall_Fives=" + RedBall_Fives +
                ", BlueBall_One=" + BlueBall_One +
                ", BlueBall_Tow=" + BlueBall_Tow +
                ", Prize_Pool_Bonus=" + Prize_Pool_Bonus +
                ", First_Prize_Number=" + First_Prize_Number +
                ", First_Prize_Bonus=" + First_Prize_Bonus +
                ", Second_Prize_Number=" + Second_Prize_Number +
                ", Second_Prize_Bonus=" + Second_Prize_Bonus +
                ", Total_Bet_Amount=" + Total_Bet_Amount +
                ", Lottery_Date='" + Lottery_Date + '\'' +
                '}';
    }
}
