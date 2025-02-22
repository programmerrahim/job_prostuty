package com.ruksana.jobprostuti;

import android.app.AlertDialog;
import android.graphics.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;


public class DetailsForQbankActivity extends AppCompatActivity {


    String pdfUrl, title, pageNumber, linkText;

//    private ProgressDialog progressDialog;


    private PDFView pdfView;
    private Switch soundSwitch;

    private Switch nightModeSwitch;

//    TextView detailsLinkText;

    private boolean isSoundEnabled = false;
    MediaPlayer pageTurnSound;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private TextView tvProgressText;
    private String fileName = "myPdfFile.pdf";  // Name for the saved file


    private static String TAG = "DetailActivity";


    private boolean isNightModeEnabled = false;


    RelativeLayout rootLayout;

    File pdfFile;


    private EditText searchEditText;
    private ImageView searchButton;

//    int startPage ; // Start at page 2
//    int endPage ; // End at page 10
//
//    int[] pageIndices;

//    private String GAME_ID = "YOUR_GAME_ID"; // Replace with your Unity Game ID
//    private boolean TEST_MODE = true; // Set to false in production

    private String GAME_ID = "1234567"; // Replace with Unity Game ID
    private String INTERSTITIAL_AD_ID = "Interstitial_Android"; // Replace with Ad Unit ID
    private boolean TEST_MODE = true; // Enable for testing


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        //int action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;


        //add back button
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FirebaseApp.initializeApp(this);


        pdfUrl = getIntent().getStringExtra("data");
        title = getIntent().getStringExtra("title");
        pageNumber = getIntent().getStringExtra("pageNumber");
        // linkText = getIntent().getStringExtra("linkText");
//        pageNumber = getIntent().getStringExtra("pageNumber");
        actionBar.setTitle(title);


        rootLayout = findViewById(R.id.rootLayoutId);


        pdfView = findViewById(R.id.pdfView);

        soundSwitch = findViewById(R.id.soundSwitch);
        nightModeSwitch = findViewById(R.id.nightSwitch);
//        detailsLinkText = findViewById(R.id.detailsLinkTextId);


//        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);

//        searchBar.setHint("পৃষ্ঠা নং লিখুন..");


        // Search functionality
        searchButton.setOnClickListener(v -> {


            openCustomDialog();


        });


        // Initialize views
        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);
        tvProgressText = findViewById(R.id.tvProgressTextId);


        // Check if the file exists locally, if not, download it
        fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);  // Extract file name from URL
        pdfFile = new File(getExternalFilesDir(null), fileName);

        if (pdfFile.exists()) {
            // Load from local file if exists
            loadPdfFromFile(pdfFile);
        } else {
            // Download and save if file doesn't exist
            downloadAndSavePdfFromUrl(pdfUrl, fileName);
        }


        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isNightModeEnabled = isChecked;

            // Check if the file exists locally, if not, download it
            fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);  // Extract file name from URL
            pdfFile = new File(getExternalFilesDir(null), fileName);

            if (pdfFile.exists()) {
                // Load from local file if exists
                loadPdfFromFile(pdfFile);
            } else {
                // Download and save if file doesn't exist
                downloadAndSavePdfFromUrl(pdfUrl, fileName);
            }

        });


//        detailsLinkText.setText(linkText);


//        // Initialize the SDK:
//        UnityAds.initialize(getApplicationContext(), unityGameID, testMode, new IUnityAdsInitializationListener() {
//            @Override
//            public void onInitializationComplete() {
//
//            }
//
//            @Override
//            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
//
//            }
//        });
//
//
//        loadInt();

//        UnityAds.initialize(this, GAME_ID, TEST_MODE);


    }//End On create


    private void openCustomDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Set up the components in the custom layout
        TextView dialogPageNumber = dialogView.findViewById(R.id.dialogPageNumberId);
        searchEditText = dialogView.findViewById(R.id.searchBar);
        Button dialogButtonYes = dialogView.findViewById(R.id.dialogButtonYes);
        Button dialogButtonNo = dialogView.findViewById(R.id.dialogButtonNo);

        dialogPageNumber.setText(pageNumber);

        AlertDialog alertDialog = builder.create();
        //alertDialog.setCancelable(false);


        // Set click listeners for the buttons
        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Yes button click

                String query = searchEditText.getText().toString();
                // Hide the keyboard
                View currentFocus = getCurrentFocus();
                if (currentFocus != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }

                if (!query.isEmpty()) {
                    int pageNumber = Integer.parseInt(query);

                    // Validate the page number
                    int totalPages = pdfView.getPageCount();
                    if (pageNumber >= 1 && pageNumber <= totalPages) {
                        pdfView.jumpTo(pageNumber - 1); // Pages are zero-indexed
                        alertDialog.dismiss();

                    } else {
                        Toast.makeText(DetailsForQbankActivity.this, "Page not found", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                } else {
                    Toast.makeText(DetailsForQbankActivity.this, "Please enter a page number", Toast.LENGTH_SHORT).show();

                }
                alertDialog.setCancelable(false);

            }
        });

        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle No button click
                // For example, dismiss the dialog
                alertDialog.dismiss();
            }
        });
        // Create and show the dialog
        alertDialog.show();


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//go previous activity, when back button of  actionbar clicked
        return super.onSupportNavigateUp();
    }

    private void loadPdfFromFile(File pdfFile) {
//        isNightModeEnabled = true;
        progressBar.setVisibility(View.GONE);
        tvProgress.setVisibility(View.GONE);
        tvProgressText.setVisibility(View.GONE);
        pdfView.setVisibility(View.VISIBLE);
        pdfView.fromFile(pdfFile)
                .enableSwipe(true) // allows to block changing pages using swipe
                .enableDoubletap(true)
                .fitEachPage(true)
                // allows to draw something on the current page, usually visible in the middle of the screen
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .onPageChange((page, pageCount) -> {
                    if (isSoundEnabled) {
                        playPageTurnSound();
                    }

                    applyFlipEffect();
                })
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                .nightMode(isNightModeEnabled) // toggle night mode
                .load();

        // Initialize sound effect
        pageTurnSound = MediaPlayer.create(this, R.raw.page_turn);
        // Switch to toggle sound effects
        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> isSoundEnabled = isChecked);


    }


    private void downloadAndSavePdfFromUrl(String pdfUrl, String fileName) {

        // Check if the URL is empty or null
        if (pdfUrl == null || pdfUrl.isEmpty()) {
            Toast.makeText(DetailsForQbankActivity.this, "PDF URL is empty. Cannot load PDF.", Toast.LENGTH_LONG).show();

        } else {
            try {

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl(pdfUrl); // Get reference from URL

                File pdfFile = new File(getExternalFilesDir(null), fileName);  // Path for saving

                storageRef.getFile(pdfFile)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Hide progress bar and text when download is complete
                            progressBar.setVisibility(View.GONE);
                            tvProgress.setVisibility(View.GONE);
                            tvProgressText.setVisibility(View.GONE);

                            // Load the PDF from the saved file
                            pdfView.setVisibility(View.VISIBLE);
                            pdfView.fromFile(pdfFile).load();
                        })
                        .addOnProgressListener(taskSnapshot -> {
                            long totalBytes = taskSnapshot.getTotalByteCount();
                            long bytesTransferred = taskSnapshot.getBytesTransferred();
                            double progress = (100.0 * bytesTransferred) / totalBytes;

                            progressBar.setProgress((int) progress);
                            tvProgress.setText(String.format("Loading %.0f%%", progress));
                        })
                        .addOnFailureListener(exception -> {
                            Log.e("PDF Download", "Failed to download PDF", exception);
                            progressBar.setVisibility(View.GONE);
                            tvProgress.setVisibility(View.GONE);
                            tvProgressText.setVisibility(View.GONE);
                        });
            } catch (Exception e) {
                Log.e("PDF Download", "Error creating file for saving", e);
            }
        }
    }

    private void playPageTurnSound() {
        if (pageTurnSound != null) {
            pageTurnSound.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pageTurnSound != null) {
            pageTurnSound.release();
            pageTurnSound = null;
        }
    }


    private void applyFlipEffect() {
        FlipAnimation flipAnimation = new FlipAnimation(pdfView);
        flipAnimation.setDuration(700); // Duration of the flip
        pdfView.startAnimation(flipAnimation);

    }

    public static class FlipAnimation extends Animation {
        private Camera camera;
        private View view;

        public FlipAnimation(View view) {
            this.view = view;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            camera = new Camera();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float centerX = view.getWidth() / 2.0f; // Center X of the view
            final float centerY = view.getHeight() / 2.0f; // Center Y of the view

            final float degrees = 0.5f * interpolatedTime; // Rotate from 0 to 180

            camera.save();
            camera.rotateY(degrees); // Rotate along Y-axis
            camera.getMatrix(t.getMatrix());
            camera.restore();

            t.getMatrix().preTranslate(-centerX, -centerY); // Move to pivot
            t.getMatrix().postTranslate(centerX, centerY); // Restore position
        }
    }

//    public void loadInt() {
//        UnityAds.load(inter_ads_id, new IUnityAdsLoadListener() {
//            @Override
//            public void onUnityAdsAdLoaded(String placementId) {
//
//            }
//
//            @Override
//            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
//                Toast.makeText(DetailsForQbankActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }


}