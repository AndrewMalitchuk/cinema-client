package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.devs.readmoreoption.ReadMoreOption;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.skyhope.materialtagview.TagView;
import com.skyhope.materialtagview.enums.TagSeparator;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

public class AboutFilmActivity extends AppCompatActivity {

    @BindView(R.id.textView4)
    TextView textView4;

    @BindView(R.id.mScrollView)
    ScrollView scrollView;

    @BindView(R.id.floatingActionButton2)
    FloatingActionButton fab;

    @BindView(R.id.linLayout)
    LinearLayout linLayout;


    private BottomSheetMenuDialog mBottomSheetDialog;
    private boolean mShowingLongDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_film);

        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        //
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});


        //
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.getYouTubePlayerWhenReady(e -> {
            e.loadVideo("bGS3n7yFNv0",0);
        });


//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                String videoId = "bGS3n7yFNv0";
//                youTubePlayer.loadVideo(videoId, 0);
//            }
//        });


        //

        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linLayout)
                .setTransitionDuration(4000)
                .start();

        //
        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(this)
                .textLength(3, ReadMoreOption.TYPE_LINE) // OR
                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel("MORE")
                .lessLabel("LESS")
                .moreLabelColor(Color.RED)
                .lessLabelColor(Color.BLUE)
                .labelUnderLine(true)
                .expandAnimation(true)
                .build();

//        readMoreOption.addReadMoreTo(textView4, getString(R.string.long_desc));
        readMoreOption.addReadMoreTo(textView4, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Elementum tempus egestas sed sed risus pretium quam vulputate dignissim. Malesuada pellentesque elit eget gravida cum. Duis ultricies lacus sed turpis tincidunt id. Scelerisque eu ultrices vitae auctor eu augue ut lectus arcu. Mattis vulputate enim nulla aliquet porttitor lacus. Fermentum et sollicitudin ac orci phasellus egestas tellus rutrum tellus. Blandit volutpat maecenas volutpat blandit aliquam etiam erat. Facilisis sed odio morbi quis commodo odio aenean. Ridiculus mus mauris vitae ultricies leo. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum enim. Ipsum dolor sit amet consectetur. Consectetur a erat nam at lectus urna. Aenean et tortor at risus viverra adipiscing at in tellus. Nibh praesent tristique magna sit amet purus gravida quis. Ornare lectus sit amet est placerat in egestas erat imperdiet. Morbi tincidunt ornare massa eget egestas purus viverra accumsan. Interdum velit euismod in pellentesque massa placerat.  Risus ultricies tristique nulla aliquet enim. At augue eget arcu dictum. Semper eget duis at tellus at urna. A cras semper auctor neque. Est pellentesque elit ullamcorper dignissim cras tincidunt lobortis. Leo urna molestie at elementum eu. Ut eu sem integer vitae justo eget magna fermentum. Id leo in vitae turpis. Ultricies lacus sed turpis tincidunt id aliquet risus. Aliquam ultrices sagittis orci a scelerisque. Tellus at urna condimentum mattis pellentesque id nibh tortor id. Sit amet facilisis magna etiam tempor orci. Sit amet luctus venenatis lectus magna fringilla. Ac ut consequat semper viverra nam libero. Est ullamcorper eget nulla facilisi etiam dignissim diam. Tempus quam pellentesque nec nam aliquam. Enim diam vulputate ut pharetra sit amet aliquam.  Integer quis auctor elit sed. Luctus venenatis lectus magna fringilla urna. Sit amet volutpat consequat mauris. Sed turpis tincidunt id aliquet risus feugiat. Eu feugiat pretium nibh ipsum consequat. Ultricies integer quis auctor elit sed vulputate mi sit amet. Non enim praesent elementum facilisis leo. Ut tortor pretium viverra suspendisse potenti nullam ac tortor. Purus non enim praesent elementum. Risus at ultrices mi tempus imperdiet nulla malesuada pellentesque. Sit amet mauris commodo quis imperdiet massa. Lectus magna fringilla urna porttitor rhoncus dolor. Orci a scelerisque purus semper eget. Elementum facilisis leo vel fringilla est ullamcorper eget nulla. Pharetra convallis posuere morbi leo urna molestie at. Nascetur ridiculus mus mauris vitae ultricies.\n");


    }

    public void onImageClick(View view){
        Intent intent=new Intent(this,ZoomImageActivity.class);
        startActivity(intent);
    }

    public void onFabClick(View view){
//        mShowingLongDialog = true;
//        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
//                .setMode(BottomSheetBuilder.MODE_LIST)
//                .setMenu(R.menu.navigation)
//                .addDividerItem()
//                .addTitleItem("Share")
//                .setItemClickListener(new BottomSheetItemClickListener() {
//                    @Override
//                    public void onBottomSheetItemClick(MenuItem item) {
//                        Log.d("Item click", item.getTitle() + "");
//                        mShowingLongDialog = false;
//                    }
//                })
//                .createDialog();
//
//        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                mShowingLongDialog = false;
//            }
//        });
//        mBottomSheetDialog.show();
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }
}
