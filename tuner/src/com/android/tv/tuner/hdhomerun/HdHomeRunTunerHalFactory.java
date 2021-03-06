/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.tv.tuner.hdhomerun;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.WorkerThread;

import com.android.tv.tuner.api.Tuner;
import com.android.tv.tuner.api.TunerFactory;

/** TunerHal factory that creates all built in tuner types. */
public final class HdHomeRunTunerHalFactory implements TunerFactory {
    public static final TunerFactory INSTANCE = new HdHomeRunTunerHalFactory();

    private HdHomeRunTunerHalFactory() {}
    /**
     * Creates a TunerHal instance.
     *
     * @param context context for creating the TunerHal instance
     * @return the TunerHal instance
     */
    @Override
    @WorkerThread
    public synchronized Tuner createInstance(Context context) {
        Tuner tunerHal = null;
        if (tunerHal == null) {
            tunerHal = new HdHomeRunTunerHal(context);
        }
        return tunerHal.openFirstAvailable() ? tunerHal : null;
    }

    /**
     * Returns if tuner input service would use built-in tuners instead of USB tuners or network
     * tuners.
     */
    @Override
    public boolean useBuiltInTuner(Context context) {
        return false;
    }

    /** Gets the number of tuner devices currently present. */
    @Override
    @WorkerThread
    public Pair<Integer, Integer> getTunerTypeAndCount(Context context) {
        return Pair.create(Tuner.TUNER_TYPE_NETWORK, HdHomeRunTunerHal.getNumberOfDevices());
    }
}
