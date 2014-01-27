package com.goodeggapps.rhythmbattle.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.vending.billing.util.IabHelper;
import com.android.vending.billing.util.IabResult;
import com.android.vending.billing.util.Inventory;
import com.android.vending.billing.util.Purchase;
import com.goodeggapps.rhythmbattle.R;
import com.google.ads.AdView;

public class UpgradeActivity extends Activity {

    public static int upgradepoints;
    public static int ULShield;
    public static int ULSpikes;
    public static boolean ULUnlockAll;
    public static int ULDevTip;
    public static SharedPreferences settings;
    public static Integer upgrade1;
    public static Integer upgrade2;
    public static Integer upgrade3;
    public static Integer upgrade4;
    public static Integer upgradeLocked;
    public static ImageView upgradeDescription;
    public static TextView currentNotes;
    public static Gallery gallery;
    IabHelper mHelper;

    //TODO all fake values from trivial drive
    static final String SKU_PREMIUM = "premium";
    static final String SKU_CHARITY = "tips";
    static final String TAG = "Rhythm Battle";
    static final int RC_REQUEST = 10001;

    
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrades);

    //TODO In-App Purchases go here!
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwW8aRhVV1skaZKYHOoVpsAGbSYccn+ORhhnPMdWFB3J7zAwW6LOT3Q0egQxEa/K2rhzfAAMx8Pz3aEkQtHCBvaeYgj6wjLuZVMgF0W6va29oGc7FxC0IVUNeoe2B/kCA8AAJM25Tr9oYnb3BiueODKkzvkiAWj1AvjTgovVW9Nvdck7gyfIQ0ZgRtfGmM5nQexEfNw/C3E77vo53w0yKRGx6AiwbRteuwPUDghBBJUl1PO7BCsouLCcQ6MiI+6ebv43xFJt/7SI+D3URx8nNybzfEMDYAgXLACTlcggsUWsR2Vf0K4BwNmRo5WxW6H1wfUzHj6W9y0suPb/iIHC2dQIDAQAB";
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                

                if (!result.isSuccess()) {
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
        


        
        settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        getValues();
        updateUi();
        currentNotes = (TextView) findViewById(R.id.currentNotes);
		Typeface font = Typeface.createFromAsset(getAssets(),"font/pf_tempesta_five.ttf");
		currentNotes.setTypeface(font);

        currentNotes.setText(" x " + upgradepoints);
        if (ULDevTip > 0) {
        AdView adView = (AdView) findViewById(R.id.adView);
        adView.setVisibility(0);
        }
        
        upgradeDescription = (ImageView) findViewById(R.id.upgradeDescription);
        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	switch(position) {
            	case 0:
            		switch (ULShield) {
            		case 0:
            			if (upgradepoints > 99){
            				upgradepoints = upgradepoints - 100;
            				ULShield = 1;
            			}
        				break;
            		case 1:
            			if (upgradepoints > 199){
            				upgradepoints = upgradepoints - 200;
            				ULShield = 2;
            			}
        				break;
            		case 2:
            			if (upgradepoints > 299){
            				upgradepoints = upgradepoints - 300;
            				ULShield = 3;
            			}
        				break;
            		case 3: 
            			if (upgradepoints > 399){
            				upgradepoints = upgradepoints - 400;
            				ULShield = 4;
            			}
        				break;
            		default:
            			break;
            		}
        				putValues();
        				getValues();
        				updateUi();
            		break;
            	case 1:
            		switch (ULSpikes) {
            		case 0:
            			if (upgradepoints > 399){
            				upgradepoints = upgradepoints - 400;
            				ULSpikes = 1;
            			}
        				break;
            		case 1:
            			if (upgradepoints > 799){
            				upgradepoints = upgradepoints - 800;
            				ULSpikes = 2;
            			}
        				break;
            		case 2:
            			if (upgradepoints > 999){
            				upgradepoints = upgradepoints - 1000;
            				ULSpikes = 3;
            			}
        				break;
            		case 3: 
            			if (upgradepoints > 1199){
            				upgradepoints = upgradepoints - 1200;
            				ULSpikes = 4;
            			}
        				break;
            		default:
            			break;
            		}
        				putValues();
        				getValues();
        				updateUi();
        			break;
            	case 2:
        	       
            		if (ULUnlockAll == false){
            		String payload = ""; 
        	        mHelper.launchPurchaseFlow(UpgradeActivity.this, SKU_PREMIUM, RC_REQUEST, 
        	                mPurchaseFinishedListener, payload);
        	        putValues();
    				getValues();
    				updateUi();
            		} else {
            			alert("You already own this!");
            		}
            		break;
            	case 3:
                    /*        for security, generate your payload here for verification. See the comments on 
                     *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use 
                     *        an empty string, but on a production app you should carefully generate this. */
                    String payload1 = ""; 
             
                    mHelper.launchPurchaseFlow(UpgradeActivity.this, SKU_CHARITY, RC_REQUEST, 
                            mPurchaseFinishedListener, payload1);
        	        putValues();
    				getValues();
    				updateUi();
            		break;
           		default:
           			break;
            }
        }});
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
            	switch(arg2) {
            	case 0:
            		switch(ULShield) {
                    case 0:
                    	upgradeDescription.setImageResource(R.drawable.shieldlvl1);
                    	break;
                    case 1:
                    	upgradeDescription.setImageResource(R.drawable.shieldlvl2);
                    	break;
                    case 2:
                    	upgradeDescription.setImageResource(R.drawable.shieldlvl3);
                    	break;
                    case 3:
                    	upgradeDescription.setImageResource(R.drawable.shieldlvl4);
                    	break;
                    case 4:
                    	upgradeDescription.setImageResource(R.drawable.shieldlvlmax);
                    	break;
                    default:
                    	upgradeDescription.setImageResource(R.drawable.shieldlvl1);
                    	break;
                    }
            		break;
            	case 1:
                    switch(ULSpikes) {
                    case 0:
                    	upgradeDescription.setImageResource(R.drawable.spikelvl1);
                    	break;
                    case 1:
                    	upgradeDescription.setImageResource(R.drawable.spikelvl2);
                    	break;
                    case 2:
                    	upgradeDescription.setImageResource(R.drawable.spikelvl3);
                    	break;
                    case 3:
                    	upgradeDescription.setImageResource(R.drawable.spikelvl4);
                    	break;
                    case 4:
                    	upgradeDescription.setImageResource(R.drawable.spikelvlmax);
                    	break;
                    default:
                    	upgradeDescription.setImageResource(R.drawable.spikelvl1);
                    	break;
                    }
                    break;
            	case 2:
            		if (ULUnlockAll == true) {
                    	upgradeDescription.setImageResource(R.drawable.allunlocked);
            		} else {
                    	upgradeDescription.setImageResource(R.drawable.unlockall);
            		}
            		break;
            	case 3:
            		if (ULDevTip > 0) {
                    	upgradeDescription.setImageResource(R.drawable.thankstip);
            		} else {
                    	upgradeDescription.setImageResource(R.drawable.pleasetip);
            		}
            		break;
           		default:
           			break;
            }
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}	
        	
        });
            
    }
    
    public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;
        
        private Integer[] mImageIds = {
                upgrade1,
                upgrade2,
                upgrade3,
                upgrade4
        };

        public ImageAdapter(Context c) {
            mContext = c;
            TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
            mGalleryItemBackground = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground, 0);
            attr.recycle();
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mImageIds[position]);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(mGalleryItemBackground);

            return imageView;
        }
    }

    public static void getValues() {
		
        upgradepoints = settings.getInt("UpgradePoints", 0);
    	ULShield = settings.getInt("ULShield", 0);
    	ULSpikes = settings.getInt("ULSpikes", 0);
    	ULUnlockAll = settings.getBoolean("ULUnlockAll", false);
        ULDevTip = settings.getInt("ULDevTip", 0);

	}

    public static void putValues() {
		
		settings.edit()
			.putInt("UpgradePoints", upgradepoints)
			.putBoolean("ULUnlockAll", ULUnlockAll)
			.putInt("ULDevTip", ULDevTip)
			.putInt("ULSpikes", ULSpikes)
			.putInt("ULShield", ULShield)
			.commit();
	}

    
    
    //TODO Trival Drive copy stuff starts here!

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (result.isFailure()) {
                return;
            }
            
            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            ULUnlockAll = (premiumPurchase != null);
            if (ULUnlockAll) {
            	ULSpikes = 4;
                ULShield = 4;
                putValues();
                updateUi();
            }
            
            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            Purchase gasPurchase = inventory.getPurchase(SKU_CHARITY);
            if (gasPurchase != null) {
                mHelper.consumeAsync(inventory.getPurchase(SKU_CHARITY), mConsumeFinishedListener);
                return;
            }
            updateUi();
        }
    };

        
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }

            if (purchase.getSku().equals(SKU_CHARITY)) {
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }

            else if (purchase.getSku().equals(SKU_PREMIUM)) {
                ULUnlockAll = true;
                ULSpikes = 4;
                ULShield = 4;
                putValues();
                updateUi();
                alert("Thank you for purchasing! Unlocked every level, upgrade, challenge mode, and all ringtones!");
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            if (result.isSuccess()) {
                ULDevTip = ULDevTip + 1;
                putValues();
                alert("Thank you for supporting us!");
            }
            updateUi();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    public void updateUi() {
        upgradeDescription = (ImageView) findViewById(R.id.upgradeDescription);
        currentNotes = (TextView) findViewById(R.id.currentNotes);
        currentNotes.setText(" x " + upgradepoints);

    	switch(ULShield) {
        case 0:
        	upgrade1 = R.drawable.upgradeshield1;
        	upgradeDescription.setImageResource(R.drawable.shieldlvl1);
        	break;
        case 1:
        	upgrade1 = R.drawable.upgradeshield2;
        	upgradeDescription.setImageResource(R.drawable.shieldlvl2);
        	break;
        case 2:
        	upgrade1 = R.drawable.upgradeshield3;
        	upgradeDescription.setImageResource(R.drawable.shieldlvl3);
        	break;
        case 3:
        	upgrade1 = R.drawable.upgradeshield4;
        	upgradeDescription.setImageResource(R.drawable.shieldlvl4);
        	break;
        case 4:
        	upgrade1 = R.drawable.upgradeshield4;
        	upgradeDescription.setImageResource(R.drawable.shieldlvlmax);
        	break;
        default:
        	upgrade1 = R.drawable.upgradeshield1;
        	upgradeDescription.setImageResource(R.drawable.shieldlvl1);
        	break;
        }
        
        switch(ULSpikes) {
        case 0:
        	upgrade2 = R.drawable.upgradespike1;
        	upgradeDescription.setImageResource(R.drawable.spikelvl1);
        	break;
        case 1:
        	upgrade2 = R.drawable.upgradespike2;
        	upgradeDescription.setImageResource(R.drawable.spikelvl2);
        	break;
        case 2:
        	upgrade2 = R.drawable.upgradespike3;
        	upgradeDescription.setImageResource(R.drawable.spikelvl3);
        	break;
        case 3:
        	upgrade2 = R.drawable.upgradespike4;
        	upgradeDescription.setImageResource(R.drawable.spikelvl4);
        	break;
        case 4:
        	upgrade2 = R.drawable.upgradespike4;
        	upgradeDescription.setImageResource(R.drawable.spikelvlmax);
        	break;
        default:
        	upgrade2 = R.drawable.upgradespike1;
        	upgradeDescription.setImageResource(R.drawable.spikelvl1);
        	break;
        }
        upgrade3 = R.drawable.upgradeall;
        upgrade4 = R.drawable.upgradespike2;
		switch(ULDevTip) {
        case 0:
        	upgrade4 = R.drawable.donatehat;
        	break;
        case 1:
        	upgrade4 = R.drawable.donate1;
        	break;
        case 2:
        	upgrade4 = R.drawable.donate2;
        	break;
        case 3:
        	upgrade4 = R.drawable.donate3;
        	break;
        case 4:
        	upgrade4 = R.drawable.donate3;
        	break;
        default:
        	upgrade4 = R.drawable.donate3;
        	break;
        }
        gallery = (Gallery) findViewById(R.id.gallery);
        int position = gallery.getSelectedItemPosition();
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setSelection(position);
    }

    void complain(String message) {
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
		bld.setIcon(R.drawable.oldicon);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
        updateUi();
    }
    
}