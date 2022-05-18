package com.careerdevs.stockapiv1.models;

/*"Symbol": "GOOGL",
  "AssetType": "Common Stock",
  "Name": "Alphabet Inc",
  "Description": "Alphabet Inc. is an American multinational conglomerate headquartered in Mountain View, California. It was created through a restructuring of Google on October 2, 2015, and became the parent company of Google and several former Google subsidiaries. The two co-founders of Google remained as controlling shareholders, board members, and employees at Alphabet. Alphabet is the world's fourth-largest technology company by revenue and one of the world's most valuable companies.",
  "CIK": "1652044",
  "Exchange": "NASDAQ",
  "Currency": "USD",
  "Country": "USA",
  "Sector": "TECHNOLOGY",
  "Industry": "SERVICES-COMPUTER PROGRAMMING, DATA PROCESSING, ETC.",
  "Address": "1600 AMPHITHEATRE PARKWAY, MOUNTAIN VIEW, CA, US",
  "FiscalYearEnd": "December",
  "LatestQuarter": "2022-03-31",
  "MarketCapitalization": "1510115508000",
  "EBITDA": "95841001000",
  "PERatio": "20.7",
  "PEGRatio": "0.806",
  "BookValue": "385.58",
  "DividendPerShare": "0",
  "DividendYield": "0",
  "EPS": "110.58",
  "RevenuePerShareTTM": "406.89",
  "ProfitMargin": "0.276",
  "OperatingMarginTTM": "0.305",
  "ReturnOnAssetsTTM": "0.15",
  "ReturnOnEquityTTM": "0.308",
  "RevenueTTM": "270334001000",
  "GrossProfitTTM": "146698000000",
  "DilutedEPSTTM": "110.58",
  "QuarterlyEarningsGrowthYOY": "-0.063",
  "QuarterlyRevenueGrowthYOY": "0.23",
  "AnalystTargetPrice": "3303.83",
  "TrailingPE": "20.7",
  "ForwardPE": "20",
  "PriceToSalesRatioTTM": "5.59",
  "PriceToBookRatio": "5.89",
  "EVToRevenue": "5.15",
  "EVToEBITDA": "13.63",
  "Beta": "1.061",
  "52WeekHigh": "3030.93",
  "52WeekLow": "2196.49",
  "50DayMovingAverage": "2561.5",
  "200DayMovingAverage": "2748.15",
  "SharesOutstanding": "300764000",
  "DividendDate": "None",
  "ExDividendDate": "None"*/

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Overview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private long Id;

   @JsonProperty("Symbol")
   @Column(name = "symbol", nullable = false, unique = true)
   private String symbol;

   @JsonProperty("AssetType")
    @Column(name = "asset_type", nullable = false)
    private String assetType;

    @JsonProperty("Name")
    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @JsonProperty("Exchange")
    @Column(name = "exchange", nullable = false)
    private String exchange;

    @JsonProperty("Currency")
    @Column(name = "currency", nullable = false)
    private String currency;

    @JsonProperty("Country")
    @Column(name = "country", nullable = false)
    private String country;

    @JsonProperty("Sector")
    @Column(name = "sector", nullable = false)
    private String sector;

    @JsonProperty("Industry")
    @Column(name = "industry", nullable = false)
    private String industry;

    @JsonProperty("MarketCapitalization")
    @Column(name = "market_cap", nullable = false)
    private String marketCap;

    @JsonProperty("52WeekHigh")
    @Column(name = "year_High", nullable = false)
    private float  yearHigh;

    @JsonProperty("52WeekLow")
    @Column(name = "year_Low", nullable = false)
    private float yearLow;

    @JsonProperty("DividendDate")
    @Column(name ="dividend_Date", nullable = false)
    private String dividendDate;

    public long getId() {
        return Id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getName() {
        return name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public float getYearHigh() {
        return yearHigh;
    }

    public float getYearLow() {
        return yearLow;
    }

    public String getDividendDate() {
        return dividendDate;
    }

    // create the JSON to String method.

    @Override
    public String toString() {
        return "Overview{" +
                "Id=" + Id +
                ", symbol='" + symbol + '\'' +
                ", assetType='" + assetType + '\'' +
                ", name='" + name + '\'' +
                ", exchange='" + exchange + '\'' +
                ", currency='" + currency + '\'' +
                ", country='" + country + '\'' +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", yearHigh=" + yearHigh +
                ", yearLow=" + yearLow +
                ", dividendDate='" + dividendDate + '\'' +
                '}';
    }


    //    "Symbol": "GOOGL",
//            "AssetType": "Common Stock",
//            "Name": "Alphabet Inc",
//            "Description": "Alphabet Inc. is an American multinational conglomerate headquartered in Mountain View, California. It was created through a restructuring of Google on October 2, 2015, and became the parent company of Google and several former Google subsidiaries. The two co-founders of Google remained as controlling shareholders, board members, and employees at Alphabet. Alphabet is the world's fourth-largest technology company by revenue and one of the world's most valuable companies.",
//
//             "Exchange": "NASDAQ",
//            "Currency": "USD",
//            "Country": "USA",
//            "Sector": "TECHNOLOGY",
//            "Industry": "SERVICES-COMPUTER PROGRAMMING, DATA PROCESSING, ETC.",
//            "MarketCapitalization": "1510115508000",
//            "52WeekHigh": "3030.93",
//            "52WeekLow": "2196.49"

}
