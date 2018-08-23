/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alexdisler.inapppurchases;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents an in-app product's listing details.
 */
public class SkuDetails {
    String mItemType;
    JSONObject mJsonParsed;
    String mTitle;
    String mDescription;
    String mSku;
    String mType;
    String mPriceCurrency;
    Double mPriceAsDecimal;
    String mPrice;
    String mSubscriptionPeriod;
    Double mIntroductoryPriceAsDecimal;
    String mIntroductoryPrice;
    String mIntroductoryPriceCycles;
    String mIntroductoryPricePeriod;

    public SkuDetails(String jsonSkuDetails) throws JSONException {
        this(IabHelper.ITEM_TYPE_INAPP, jsonSkuDetails);
    }

    public SkuDetails(String itemType, String jsonSkuDetails) throws JSONException {
        mItemType = itemType;
        mJsonParsed = new JSONObject(jsonSkuDetails);
        mSku = mJsonParsed.optString("productId");
        mType = mJsonParsed.optString("type");  
        mPriceCurrency = mJsonParsed.optString("price_currency_code");
        mPrice = mJsonParsed.optString("price");
        mPriceAsDecimal = Double.parseDouble(mJsonParsed.optString("price_amount_micros"))/Double.valueOf(1000000);
        if(getIsSubscription()) {
            mSubscriptionPeriod = mJsonParsed.optString("subscriptionPeriod");
        }
        if(mJsonParsed.has("introductoryPrice")) {
            mIntroductoryPricePeriod = mJsonParsed.optString("introductoryPricePeriod");
            mIntroductoryPriceCycles = mJsonParsed.optString("introductoryPriceCycles");
            mIntroductoryPrice = mJsonParsed.optString("introductoryPrice");
            mIntroductoryPriceAsDecimal = Double.parseDouble(mJsonParsed.optString("introductoryPriceAmountMicros"))/Double.valueOf(1000000);
        }
        mTitle = mJsonParsed.optString("title");
        mDescription = mJsonParsed.optString("description");
    }

    public String getSku() { return mSku; }
    public String getType() { return mType; }
    public String getPrice() { return mPrice; }
    public String getPriceCurrency() { return mPriceCurrency; }
    public Double getPriceAsDecimal() { return mPriceAsDecimal; }
    public String getTitle() { return mTitle; }
    public String getDescription() { return mDescription; }
    public Boolean getIsSubscription() { return mType.contentEquals("subs"); }
    public Boolean getHasIntroductoryPrice() { return mJsonParsed.has("introductoryPrice"); }
    public Double getIntroductoryPriceAsDecimal() { return mIntroductoryPriceAsDecimal; }
    public String getIntroductoryPrice() { return mIntroductoryPrice; }
    public String getIntroductoryPriceCycles() { return mIntroductoryPriceCycles; }
    public String getIntroductoryPricePeriod() { return mIntroductoryPricePeriod; }
    public String getSubscriptionPeriod() { return mSubscriptionPeriod; }

    @Override
    public String toString() {
        return "SkuDetails:" + mJsonParsed.toString();
    }

    public JSONObject toJson() {
        return mJsonParsed;
    }
}
