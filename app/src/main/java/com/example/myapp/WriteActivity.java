package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WriteActivity extends AppCompatActivity {
    //User
    String userName,userID;

    //Article
    private EditText contents,title,name,age;
    private RadioGroup radioGroup;
    private Button camera;
    private ImageView selectedImage;
    private Button btn_save;


    //Database
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private UploadTask uploadTask;
    private Uri file;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private StorageReference articleRef;
    private Uri downloadUri;
    private String imageFilePath;
    private Uri cameraUri;

    private Calendar c = Calendar.getInstance();
    private Article article;
    private static SimpleDateFormat dateFormat;
    private Long now = System.currentTimeMillis();
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private ProgressDialog pdialog=null;

    //fragment
    private FragmentManager fragmentManager;
    private Main_fragment fragmentMain;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        initView();

        //for user info
        Intent intent = getIntent();
        userName=intent.getStringExtra("userName");
        userID=intent.getStringExtra("userID");

        //image upload
        tedPermission();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog();
            }
        });

        //?????? ?????? -> article ??????
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contents.getText().toString().equals("")) {
                    Toast.makeText(WriteActivity.this, "??????????????? ???????????????!", Toast.LENGTH_SHORT).show();
                    return;
                }

                btn_save.setEnabled(false);
                Long now1 = System.currentTimeMillis();


                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton rb =(RadioButton)findViewById(id);

                //Log.e("##",getCurrentDate());
                article = new Article(Long.toString(now1), userID, userName, contents.getText().toString(), age.getText().toString(),
                        title.getText().toString(), name.getText().toString(),rb.getText().toString(), "", getCurrentDate());
                if(file!=null){
                    upload(file, now1);
                }
                else {
                    uploadArticle(article,now1);
                }
            }
        });
    }

    private void initView() {
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        age=findViewById(R.id.age);
        name=findViewById(R.id.name);
        title=findViewById(R.id.title);
        contents = findViewById(R.id.contents);
        radioGroup=findViewById(R.id.radioGroup);

        btn_save = findViewById(R.id.btn_save);
        camera = this.findViewById(R.id.camera);
        selectedImage = findViewById(R.id.selectedImage);

        //activity to fragment
        fragmentManager = getSupportFragmentManager();
        fragmentMain= new Main_fragment();
        transaction=fragmentManager.beginTransaction();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item ){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String getCurrentDate(){
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
    public void uploadArticle(Article article,Long now1){
        databaseReference.child("Articles").child(Long.toString(now1)).setValue(article).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Log.v("##", "finish");
                Toast.makeText(WriteActivity.this,"????????? ??????",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void upload(Uri file, final Long now1){
        //Log.v("##", "upload()");
        pdialog=new ProgressDialog(WriteActivity.this);
        pdialog.setTitle("????????? ????????????");
        pdialog.show();
        uploadTask = articleRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(WriteActivity.this, "??????", Toast.LENGTH_SHORT).show();
                //Log.v("123123", "fail");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Log.v("132123", "???");
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        Log.v("12312", "!!!");
                        return articleRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            task.addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        downloadUri =  task.getResult();
                                        Log.v("123123", "success");
                                        c.setTimeInMillis(now1);
                                        c.add(Calendar.DATE, 7);
                                        Log.v("123123", "before null");
                                        assert downloadUri != null;
                                        Log.v("123123", "after null");
                                        article.setImage(downloadUri.toString());
                                        databaseReference.child("Articles").child(Long.toString(now1)).setValue(article).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.v("123213", "finish");
                                                Toast.makeText(WriteActivity.this,"????????? ??????",Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            // Handle failures
                            // ...
                            Log.v("123123", "why not?");
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (pdialog!=null&&pdialog.isShowing()) {
            pdialog.dismiss();
        }
        super.onDestroy();
    }

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // ?????? ?????? ??????
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }


        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void makeDialog(){

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(WriteActivity.this);
        alt_bld.setTitle("?????? ?????????").setCancelable(false).setPositiveButton("????????????", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.v("??????", "??????????????? > ???????????? ??????");
                // ?????? ?????? ??????
                takePhoto();

            }

        }).setNeutralButton("????????????", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                Log.v("??????", "??????????????? > ???????????? ??????");
                //???????????? ??????
                goToAlbum();
            }
        }).setNegativeButton("??????   ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.v("??????", "??????????????? > ?????? ??????");
                // ?????? ??????. dialog ??????.
                dialog.cancel();
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
    private void takePhoto() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            File photoFile=null;
            try{
                photoFile=createImageFile();
            }catch(IOException e){
                e.printStackTrace();
            }
            if (photoFile != null) {
                cameraUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);
            }
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case PICK_FROM_ALBUM : {
                //???????????? ????????????
                //Uri uri=data.getData();
                file = data.getData();
                if(file!=null){
                    // file=Uri.fromFile(new File(getPath(uri)));
                    try{
                        Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),file);


                        //selectedImage.setImageBitmap(bitmap);
                        //selectedImage.setVisibility(View.VISIBLE);

                        Glide.with(this).load(file).into(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    articleRef=storageRef.child("articles/"+file.getLastPathSegment());
                    //Log.e("Error",articleRef.toString());
                }
                break;
            }
            case PICK_FROM_CAMERA : {
                Bitmap bitmap= BitmapFactory.decodeFile(imageFilePath);
                ExifInterface exif=null;
                try{
                    exif=new ExifInterface(imageFilePath);
                }catch(IOException e){
                    e.printStackTrace();
                }
                int exifOrientation;
                int exifDegree;
                if(exif!=null){
                    exifOrientation=exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
                    exifDegree=exifOrientationToDegree(exifOrientation);
                }
                else{
                    exifDegree=0;
                }
                //Log.e("errorWrite","??????");
                bitmap=rotate(bitmap,exifDegree);
                selectedImage.setImageBitmap(bitmap);
                //Log.e("errorWrite","????????????");
                String imageSaveUri=MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"?????? ??????","???????????????");
                Uri uri = Uri.parse(imageSaveUri);
                file = uri;
                articleRef=storageRef.child("articles/"+file.getLastPathSegment());
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
                break;
            }
        }
    }

    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        assert cursor != null;
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }
    private Bitmap rotate(Bitmap bitmap, float degree){
        Matrix matrix=new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    private int exifOrientationToDegree(int exifOrientation){
        if(exifOrientation== ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }
        else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }
        else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

}