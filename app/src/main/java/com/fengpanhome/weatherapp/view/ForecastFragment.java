package com.fengpanhome.weatherapp.view;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengpanhome.weatherapp.R;
import com.fengpanhome.weatherapp.controller.YahooWeatherInquiry;
import com.fengpanhome.weatherapp.model.AviationInfo;
import com.fengpanhome.weatherapp.model.WeatherForecast;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;



public class ForecastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Serializable
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private String location;
    private String unit;
    private AviationInfo aviationInfo;

    public String getLocation()
    {
        return location;
    }

    public String getUnit()
    {
        return unit;
    }

    private class GetForecastTask extends AsyncTask<String, Void, WeatherForecast>
    {

        @Override
        protected WeatherForecast doInBackground(String... params)
        {
            YahooWeatherInquiry yahooWeatherInquiry = new YahooWeatherInquiry(params[0], params[1]);
            try
            {
                WeatherForecast ret = yahooWeatherInquiry.getFullForecast();
                if (ret != null)
                    return ret;
            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(WeatherForecast forecast)
        {
            if (forecast != null)
            {
                ArrayList<WeatherForecast> weatherForecastList = new ArrayList<>();
                weatherForecastList.add(forecast);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new WeatherForecastAdapter(weatherForecastList));
            }
        }
    }
    public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>
    {
        private static final int FORECAST_VIEW = 0;
        private static final int METAR_VIEW = 1;
        private static final int TAF_VIEW = 2;
        private static final int AREA_FORECAST_VIEW = 3;

        private ArrayList<WeatherForecast> weatherForecastList;

        public WeatherForecastAdapter(ArrayList<WeatherForecast> weatherForecastList)
        {
            this.weatherForecastList = weatherForecastList;
        }

        @Override
        public int getItemViewType(int position)
        {
            return position;
        }

        @Override
        public WeatherForecastViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View v;
            switch(viewType)
            {
                case FORECAST_VIEW:
                {
                    v = layoutInflater.inflate(R.layout.forecast_card_layout, viewGroup, false);
                    return new WeatherForecastViewHolder(v, viewType);
                }
                case METAR_VIEW:
                {
                    v = layoutInflater.inflate(R.layout.metar_card_layout, viewGroup, false);
                    return new WeatherForecastViewHolder(v, viewType);
                }
                case TAF_VIEW:
                {
                    v = layoutInflater.inflate(R.layout.metar_card_layout, viewGroup, false);
                    return new WeatherForecastViewHolder(v, viewType);
                }
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(WeatherForecastViewHolder holder, int i)
        {
            switch (i)
            {
                case FORECAST_VIEW:
                {
                    holder.cityName.setText(weatherForecastList.get(i).getWeatherForecastCurrent().getCityName());
                    holder.skyCondition.setText(weatherForecastList.get(i).getWeatherForecastCurrent().getSkyCondition());
                    holder.wind.setText(weatherForecastList.get(i).getWeatherForecastCurrent().getWind());
                    holder.temp.setText(weatherForecastList.get(i).getWeatherForecastCurrent().getTemperature());
                    holder.temp.setCompoundDrawablesWithIntrinsicBounds(weatherForecastList.get(i).getWeatherForecastCurrent().getIconDrawable(), 0, 0, 0);
                    holder.precipitation.setText(weatherForecastList.get(i).getWeatherForecastCurrent().getHumidity());

                    holder.daysOfWeekDayOne.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(0).getDayOfWeek());
                    holder.daysOfWeekDayTwo.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(1).getDayOfWeek());
                    holder.daysOfWeekDayThree.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(2).getDayOfWeek());
                    holder.daysOfWeekDayFour.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(3).getDayOfWeek());
                    holder.daysOfWeekDayFive.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(4).getDayOfWeek());

                    holder.miniIconDayOne.setImageResource(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(0).getIconDrawable());
                    holder.miniIconDayTwo.setImageResource(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(1).getIconDrawable());
                    holder.miniIconDayThree.setImageResource(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(2).getIconDrawable());
                    holder.miniIconDayFour.setImageResource(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(3).getIconDrawable());
                    holder.miniIconDayFive.setImageResource(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(4).getIconDrawable());

                    holder.tempMinDayOne.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(0).getTempMin());
                    holder.tempMinDayTwo.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(1).getTempMin());
                    holder.tempMinDayThree.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(2).getTempMin());
                    holder.tempMinDayFour.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(3).getTempMin());
                    holder.tempMinDayFive.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(4).getTempMin());

                    holder.tempMaxDayOne.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(0).getTempMax());
                    holder.tempMaxDayTwo.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(1).getTempMax());
                    holder.tempMaxDayThree.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(2).getTempMax());
                    holder.tempMaxDayFour.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(3).getTempMax());
                    holder.tempMaxDayFive.setText(weatherForecastList.get(i).getWeatherForecastFiveDayArrayList().get(4).getTempMax());

                    holder.sunrise.setText(weatherForecastList.get(i).getWeatherForecastCurrent().getSunriseTime());
                    holder.sunset.setText(weatherForecastList.get(i).getWeatherForecastCurrent().getSunsetTime());
                    break;
                }
                case METAR_VIEW:
                {
                    holder.metarTitle.setText(R.string.metar_title);
                    TextView temp = new TextView(getActivity());
                    temp.setText(R.string.area_forecast_text);
                    temp.setTextSize(16);
                    holder.metarLayout.addView(temp);
                    break;
                }
                case TAF_VIEW:
                {
                    holder.metarTitle.setText(R.string.taf_title);
                    holder.metarTafCard.setBackgroundColor(getResources().getColor(R.color.LightGreen));
                    TextView temp = new TextView(getActivity());
                    temp.setText(R.string.area_forecast_text);
                    temp.setTextSize(16);
                    holder.metarLayout.addView(temp);
                    break;
                }
                default:
                    break;
            }
        }

        @Override
        public int getItemCount()
        {
            return weatherForecastList.size();
        }


        public class WeatherForecastViewHolder extends RecyclerView.ViewHolder
        {
            public TextView cityName;
            public TextView skyCondition;
            public TextView wind;
            public TextView temp;
            public TextView precipitation;
            public TextView daysOfWeekDayOne;
            public TextView daysOfWeekDayTwo;
            public TextView daysOfWeekDayThree;
            public TextView daysOfWeekDayFour;
            public TextView daysOfWeekDayFive;
            public ImageView miniIconDayOne;
            public ImageView miniIconDayTwo;
            public ImageView miniIconDayThree;
            public ImageView miniIconDayFour;
            public ImageView miniIconDayFive;
            public TextView tempMinDayOne;
            public TextView tempMinDayTwo;
            public TextView tempMinDayThree;
            public TextView tempMinDayFour;
            public TextView tempMinDayFive;
            public TextView tempMaxDayOne;
            public TextView tempMaxDayTwo;
            public TextView tempMaxDayThree;
            public TextView tempMaxDayFour;
            public TextView tempMaxDayFive;
            public TextView sunrise;
            public TextView sunset;

            public TextView metarTitle;
            public LinearLayout metarLayout;
            public CardView metarTafCard;
            public WeatherForecastViewHolder(View itemView, int viewType)
            {
                super(itemView);
                switch (viewType)
                {
                    case FORECAST_VIEW:
                    {
                        cityName = (TextView) itemView.findViewById(R.id.city_name);
                        skyCondition = (TextView) itemView.findViewById(R.id.sky_condition);
                        wind = (TextView) itemView.findViewById(R.id.wind);
                        temp = (TextView) itemView.findViewById(R.id.temp);
                        precipitation = (TextView) itemView.findViewById(R.id.humidity);
                        daysOfWeekDayOne = (TextView) itemView.findViewById(R.id.dayone);
                        daysOfWeekDayTwo = (TextView) itemView.findViewById(R.id.daytwo);
                        daysOfWeekDayThree = (TextView) itemView.findViewById(R.id.daythree);
                        daysOfWeekDayFour = (TextView) itemView.findViewById(R.id.dayfour);
                        daysOfWeekDayFive = (TextView) itemView.findViewById(R.id.dayfive);
                        miniIconDayOne = (ImageView) itemView.findViewById(R.id.dayoneicon);
                        miniIconDayTwo = (ImageView) itemView.findViewById(R.id.daytwoicon);
                        miniIconDayThree = (ImageView) itemView.findViewById(R.id.daythreeicon);
                        miniIconDayFour = (ImageView) itemView.findViewById(R.id.dayfouricon);
                        miniIconDayFive = (ImageView) itemView.findViewById(R.id.dayfiveicon);
                        tempMinDayOne = (TextView) itemView.findViewById(R.id.dayonetempmin);
                        tempMinDayTwo = (TextView) itemView.findViewById(R.id.daytwotempmin);
                        tempMinDayThree = (TextView) itemView.findViewById(R.id.daythreetempmin);
                        tempMinDayFour = (TextView) itemView.findViewById(R.id.dayfourtempmin);
                        tempMinDayFive = (TextView) itemView.findViewById(R.id.dayfivetempmin);
                        tempMaxDayOne = (TextView) itemView.findViewById(R.id.dayonetempmax);
                        tempMaxDayTwo = (TextView) itemView.findViewById(R.id.daytwotempmax);
                        tempMaxDayThree = (TextView) itemView.findViewById(R.id.daythreetempmax);
                        tempMaxDayFour = (TextView) itemView.findViewById(R.id.dayfourtempmax);
                        tempMaxDayFive = (TextView) itemView.findViewById(R.id.dayfivetempmax);
                        sunrise = (TextView) itemView.findViewById(R.id.sunrise);
                        sunset = (TextView) itemView.findViewById(R.id.sunset);
                        break;
                    }
                    case METAR_VIEW:
                    case TAF_VIEW:
                    {
                        metarTitle = (TextView) itemView.findViewById(R.id.metar_title);
                        metarLayout = (LinearLayout) itemView.findViewById(R.id.metar_content_layout);
                        metarTafCard = (CardView) itemView.findViewById(R.id.metar_taf_card);
                        break;
                    }
                }
            }
        }
    }

    public ForecastFragment() {}

    public static ForecastFragment newInstance(String location, String unit)
    {
        ForecastFragment ret = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString("LOCATION", location);
        args.putString("UNIT", unit);
        ret.setArguments(args);
        return ret;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        location = getArguments().getString("LOCATION");
        unit = getArguments().getString("UNIT");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.forecast_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new WeatherForecastAdapter(new ArrayList<WeatherForecast>()));
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe_container);
        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        String[] params = new String[] {location, unit};
        swipeRefreshLayout.setColorSchemeResources(R.color.CornflowerBlue, R.color.Green, R.color.Pink, R.color.DarkKhaki);
        swipeRefreshLayout.setOnRefreshListener(this);
        new GetForecastTask().execute(params);
    }

    @Override
    public void onRefresh()
    {
        swipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                new GetForecastTask().execute(location, unit);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 4000);

    }
}
