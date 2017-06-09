package com.example.farazahmed.paginationrecyclerview.Acitivites;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.farazahmed.paginationrecyclerview.Adaptor.MoviesAdaptor;
import com.example.farazahmed.paginationrecyclerview.Model.Movie;
import com.example.farazahmed.paginationrecyclerview.Model.MovieResponse;
import com.example.farazahmed.paginationrecyclerview.R;
import com.example.farazahmed.paginationrecyclerview.Rest.RetrofitClient;
import com.example.farazahmed.paginationrecyclerview.Rest.RetrofitInterface;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private final static String API_KEY = "46ad34bde863515d718c63e688c1c144";

    private RecyclerView recyclerView;
    public MoviesAdaptor adaptor;
    private List<Movie> movies;


    private int currentPage = 0;
    private int TOTAL_PAGE = 0;
    private boolean isLoading = false;
    private boolean hasLoadedAllItems = false;
    public SwipeRefreshLayout refreshLayout;
    Paginate.Callbacks callbacks = new Paginate.Callbacks() {
        @Override
        public void onLoadMore() {
            isLoading = true;
            loadDatafromApi();
        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }

        @Override
        public boolean hasLoadedAllItems() {
            return hasLoadedAllItems;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


        //attaching adaptor to recyclerview
        movies = new ArrayList<>();
        adaptor = new MoviesAdaptor(MainActivity.this, movies);
        recyclerView.setAdapter(adaptor);


        //Applying Pagination Via Paginate
        Paginate.with(recyclerView, callbacks)
                .addLoadingListItem(true)
                .setLoadingTriggerThreshold(3)
                .build();

        //adding swipe to refresh
        SwipeRefreshLayout.OnRefreshListener onRefreshlistener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                hasLoadedAllItems = false;
                loadDatafromApi();
                refreshLayout.setRefreshing(false);


            }
        };
        refreshLayout.setOnRefreshListener(onRefreshlistener);

    }

    private void initView() {
        ImageView imageview= (ImageView)findViewById(R.id.image);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadDatafromApi() {

        currentPage++;
        // attaching retrofit cleint with interface :: means baseURl with it parameters
        RetrofitInterface service = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        //call to network
        Call<MovieResponse> call = service.getTopRatedMovies(API_KEY, currentPage);

        //starting async task to get response :: Response has two condition OnResponse() or OnFailure
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                //setting the value false because want to run loaddata()

                isLoading = false;
                currentPage = response.body().getPage();
                TOTAL_PAGE = response.body().getTotalPages();
                int oldSize = movies.size();
                if (currentPage == TOTAL_PAGE) {
                    hasLoadedAllItems = true;
                }
                if (currentPage == 1) {
                    movies.clear();
                }
                movies.addAll(response.body().getResults());

                if (currentPage == 1) {
                    adaptor.notifyDataSetChanged();
                } else {
                    adaptor.notifyItemRangeInserted(oldSize - 1, response.body().getResults().size());
                    //adaptor.notifyItemMoved(oldSize-1 , response.body().getResults().size());
                }


                Log.d(TAG, movies.toString());

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                isLoading = false;
                hasLoadedAllItems = true;
                adaptor.notifyDataSetChanged();

            }
        });

    }

}
