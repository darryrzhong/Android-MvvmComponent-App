package com.drz.main.ui


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.drz.main.R
import com.drz.main.bean.CustomBean
import com.drz.main.viewholder.CustomPageViewHolder
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.adapter.OnPageChangeListenerAdapter
import com.zhpan.bannerview.constants.TransformerStyle
import com.zhpan.bannerview.holder.HolderCreator
import com.zhpan.bannerview.utils.BannerUtils
import com.zhpan.indicator.enums.IndicatorSlideMode
import kotlinx.android.synthetic.main.main_activity_guide.*

import java.util.ArrayList
import java.util.Random

class GuideActivity : BaseDataActivity(), HolderCreator<CustomPageViewHolder> {

    private lateinit var mViewPager: BannerViewPager<CustomBean, CustomPageViewHolder>

    private val des = arrayOf("在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人")

    private val transforms = intArrayOf(TransformerStyle.NONE, TransformerStyle.ACCORDION, TransformerStyle.STACK, TransformerStyle.DEPTH, TransformerStyle.ROTATE, TransformerStyle.SCALE_IN)

    private val data: List<CustomBean>
        get() {
            val list = ArrayList<CustomBean>()
            for (i in mDrawableList.indices) {
                val customBean = CustomBean()
                customBean.imageRes = mDrawableList[i]
                customBean.imageDescription = des[i]
                list.add(customBean)
            }
            return list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_guide)
        ImmersionBar.with(this)
                .titleBar(findViewById<View>(R.id.top_view))
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init()
        setupViewPager()
        updateUI(0)
        mViewPager.create(data)
    }

    private fun setupViewPager() {
        mViewPager = findViewById(R.id.viewpager)
        mViewPager.setAutoPlay(false)
                .setCanLoop(false)
                .setPageTransformerStyle(transforms[Random().nextInt(6)])
                .setScrollDuration(ANIMATION_DURATION)
                .setIndicatorMargin(0, 0, 0, resources.getDimension(R.dimen.main_dp_100).toInt())
                .setIndicatorGap(resources.getDimension(R.dimen.main_dp_10).toInt())
                .setIndicatorColor(ContextCompat.getColor(this, R.color.common_colorWhite),
                        ContextCompat.getColor(this, R.color.main_white_alpha_75))
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorRadius(resources.getDimension(R.dimen.main_dp_3).toInt(), resources.getDimension(R.dimen.main_dp_4_5).toInt())
                .setOnPageChangeListener(object : OnPageChangeListenerAdapter() {
                    override fun onPageSelected(position: Int) {
                        BannerUtils.log("position:$position")
                        updateUI(position)
                    }
                })
                .setHolderCreator(this)
                .create(data)
    }

    fun onClick(view: View) {
        MainActivity.start(this)
        finish()
    }

    private fun updateUI(position: Int) {
        tv_describe?.text = des[position]
        val translationAnim = ObjectAnimator.ofFloat(tv_describe, "translationX", -120f, 0f)
        translationAnim.duration = ANIMATION_DURATION.toLong()
        translationAnim.interpolator = DecelerateInterpolator()
        val alphaAnimator1 = ObjectAnimator.ofFloat(tv_describe, "alpha", 0f, 1f)
        alphaAnimator1.duration = ANIMATION_DURATION.toLong()
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationAnim, alphaAnimator1)
        animatorSet.start()

        if (position == mViewPager.list.size - 1 && btn_start?.visibility == View.GONE) {
            btn_start?.visibility = View.VISIBLE
            ObjectAnimator
                    .ofFloat(btn_start, "alpha", 0f, 1f)
                    .setDuration(ANIMATION_DURATION.toLong()).start()
        } else {
            btn_start?.visibility = View.GONE
        }
    }

    override fun createViewHolder(): CustomPageViewHolder {
        val customPageViewHolder = CustomPageViewHolder()
        customPageViewHolder.setOnSubViewClickListener { _, position -> Toast.makeText(this, "Logo Clicked,Item: $position", Toast.LENGTH_SHORT).show() }
        return customPageViewHolder
    }

    companion object {

        private const val ANIMATION_DURATION = 1300
    }
}
