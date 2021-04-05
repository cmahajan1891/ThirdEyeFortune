package com.third.eye.thirdeyefortune.ui.main.activities

import android.os.Bundle
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.di.components.ActivityComponent
import com.third.eye.thirdeyefortune.ui.base.BaseActivity
import com.third.eye.thirdeyefortune.ui.main.viewModels.ThirdEyeFortuneActivityViewModel

class ThirdEyeFortuneActivity : BaseActivity<ThirdEyeFortuneActivityViewModel>() {

    override fun provideLayoutId(): Int = R.layout.third_eye_fortune_activity

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
    }

}
