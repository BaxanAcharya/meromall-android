package com.example.meromall;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meromall.adaptar.CategoryAdaptar;
import com.example.meromall.adaptar.GridProductLayoutAdaptar;
import com.example.meromall.adaptar.HorizontalProductScrollAdaptar;
import com.example.meromall.adaptar.SliderAdaptar;
import com.example.meromall.model.Category;
import com.example.meromall.model.HorizontalProductScroll;
import com.example.meromall.model.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MallFragment extends Fragment {
    public MallFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdaptar categoryAdaptar;
    ////// banner slider
    private ViewPager bannerSilderViewPager2;
    private List<Slider> sliderList = new ArrayList<>();
    private Timer timer;
    private final long DELAY_TIME = 3000;
    private final long PERIOD_TIME = 3000;
    //first banner is at index 2
    private int currentPage = 2;
    ////

    ////strip ad
    private ImageView stripImage;
    private ConstraintLayout stripAdContainer;
    ///

    ///horizontal product
    private TextView horizontalLayoutTitle;
    private Button horizontalviewAllButton;
    private RecyclerView horizontalRecyclerView;
    //////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mall, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("link", "Home"));
        categories.add(new Category("link", "Applicance"));
        categories.add(new Category("link", "Furniture"));
        categories.add(new Category("link", "Fashion"));
        categories.add(new Category("link", "Toys"));
        categories.add(new Category("link", "Sports"));
        categories.add(new Category("link", "Wall Arts"));
        categories.add(new Category("link", "Books"));
        categories.add(new Category("link", "Shoes"));
        categoryAdaptar = new CategoryAdaptar(categories);
        categoryRecyclerView.setAdapter(categoryAdaptar);
        categoryAdaptar.notifyDataSetChanged();

        //////banner and slider
        bannerSilderViewPager2 = view.findViewById(R.id.banner_view_pager);
        sliderList.add(new Slider(R.drawable.ic_order_black, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.ic_cart_black, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.icon1, "#8F2E49"));

        sliderList.add(new Slider(R.drawable.icon, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.banner, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.ic_email_red, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.ic_email_green, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.ic_account_black, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.ic_mall_black, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.ic_reward_black, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.ic_order_black, "#8F2E49"));

        sliderList.add(new Slider(R.drawable.ic_cart_black, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.icon1, "#8F2E49"));
        sliderList.add(new Slider(R.drawable.icon, "#8F2E49"));

        SliderAdaptar sliderAdaptar = new SliderAdaptar(sliderList);
        bannerSilderViewPager2.setAdapter(sliderAdaptar);
        bannerSilderViewPager2.setClipToPadding(false);
        bannerSilderViewPager2.setPageMargin(20);
        bannerSilderViewPager2.setCurrentItem(currentPage);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //when view pager is idle
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pageLooper();

                }
            }
        };
        bannerSilderViewPager2.addOnPageChangeListener(onPageChangeListener);
        startBannerSlideshow();
        bannerSilderViewPager2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerSlideShow();
                //fingure removed from element
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startBannerSlideshow();
                }
                return false;
            }
        });
        //////

        ////// strip ad
        stripImage = view.findViewById(R.id.strip_ad_image);
        stripAdContainer = view.findViewById(R.id.strip_ad_container);
        stripImage.setImageResource(R.drawable.banner);
        stripAdContainer.setBackgroundColor(Color.parseColor("#ffaa2f"));
        ////////


        ///horizontal product
        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalviewAllButton = view.findViewById(R.id.horizontal_scroll_viewall_button);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerview);

        List<HorizontalProductScroll> horizontalProductScrolls = new ArrayList<>();
        horizontalProductScrolls.add(new HorizontalProductScroll(R.drawable.macbook, "Mac Book", "250 SSD Intel Processor", "Re. 250000"));
        horizontalProductScrolls.add(new HorizontalProductScroll(R.drawable.ic_account_black, "Mac Book", "250 SSD Intel Processor", "Re. 250000"));
        horizontalProductScrolls.add(new HorizontalProductScroll(R.drawable.ic_cart_black, "Mac Book", "250 SSD Intel Processor", "Re. 250000"));
        horizontalProductScrolls.add(new HorizontalProductScroll(R.drawable.ic_exit_black, "Mac Book", "250 SSD Intel Processor", "Re. 250000"));
        horizontalProductScrolls.add(new HorizontalProductScroll(R.drawable.ic_mall_black, "Mac Book", "250 SSD Intel Processor", "Re. 250000"));
        horizontalProductScrolls.add(new HorizontalProductScroll(R.drawable.macbook, "Mac Book", "250 SSD Intel Processor", "Re. 250000"));
        horizontalProductScrolls.add(new HorizontalProductScroll(R.drawable.macbook, "Mac Book", "250 SSD Intel Processor", "Re. 250000"));
        HorizontalProductScrollAdaptar horizontalProductScrollAdaptar = new HorizontalProductScrollAdaptar(horizontalProductScrolls);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager1);
        horizontalRecyclerView.setAdapter(horizontalProductScrollAdaptar);
        horizontalProductScrollAdaptar.notifyDataSetChanged();
        //////horizontal product layout

        //Grid view product layout
        TextView gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutButton = view.findViewById(R.id.grid_product_layout_button);
        GridView gridView = view.findViewById(R.id.grid_product_layout_gridview);
        gridView.setAdapter(new GridProductLayoutAdaptar(horizontalProductScrolls));
        ////////


        ////////////////
        RecyclerView recyclerViewTesting = view.findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTesting.setLayoutManager(testingLayoutManager);
        //////////////
        return view;
    }

    ///////banner slider
    private void pageLooper() {
        if (currentPage == sliderList.size() - 2) {
            currentPage = 2;
            bannerSilderViewPager2.setCurrentItem(currentPage, false);
        }
        if (currentPage == 1) {
            currentPage = sliderList.size() - 3;
            bannerSilderViewPager2.setCurrentItem(currentPage, false);
        }
    }

    private void startBannerSlideshow() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderList.size()) {
                    currentPage = 1;
                }
                bannerSilderViewPager2.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    private void stopBannerSlideShow() {
        timer.cancel();
    }
    ///////
}