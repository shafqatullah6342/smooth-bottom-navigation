package com.example.custombottomnavigation

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size

class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val items = mutableListOf<LinearLayout>()
    private val icons = mutableListOf<ImageView>()
    private val labels = mutableListOf<TextView>()
    private val dots = mutableListOf<View>()
    private var selectedIndex = -1

    var onItemSelected: ((index: Int) -> Unit)? = null

    private var defaultSelected = 1
    private var cornerRadius = 36f

    init {
        LayoutInflater.from(context).inflate(R.layout.view_custom_bottom_nav, this, true)

        items.add(findViewById(R.id.item0))
        items.add(findViewById(R.id.item1))
        items.add(findViewById(R.id.item2))
        items.add(findViewById(R.id.item3))
        items.add(findViewById(R.id.item4))

        icons.add(findViewById(R.id.icon0))
        icons.add(findViewById(R.id.icon1))
        icons.add(findViewById(R.id.icon2))
        icons.add(findViewById(R.id.icon3))
        icons.add(findViewById(R.id.icon4))

        labels.add(findViewById(R.id.label0))
        labels.add(findViewById(R.id.label1))
        labels.add(findViewById(R.id.label2))
        labels.add(findViewById(R.id.label3))
        labels.add(findViewById(R.id.label4))

        dots.add(findViewById(R.id.dot0))
        dots.add(findViewById(R.id.dot1))
        dots.add(findViewById(R.id.dot2))
        dots.add(findViewById(R.id.dot3))
        dots.add(findViewById(R.id.dot4))

        var iconTint: Int? = null
        var pillStrokeWidth = context.resources.getDimensionPixelSize(R.dimen.default_pill_stroke_width)
        var pillStrokeColor = ContextCompat.getColor(context, R.color.default_pill_stroke_color)
        var pillBgColor = ContextCompat.getColor(context, R.color.default_pill_bg_color)
        var menuResId = 0
        // Set default corner radius in dp (will be converted to pixels)
        val defaultCornerRadiusDp = 36
        val defaultCornerRadiusPx = (defaultCornerRadiusDp * context.resources.displayMetrics.density).toInt()

        // Read custom attributes from XML
        if (attrs != null) {
            context.withStyledAttributes(attrs, R.styleable.CustomBottomNavigationView) {
                pillStrokeWidth = getDimensionPixelSize(
                    R.styleable.CustomBottomNavigationView_pillStrokeWidth,
                    pillStrokeWidth
                )
                pillStrokeColor = getColor(
                    R.styleable.CustomBottomNavigationView_pillStrokeColor,
                    pillStrokeColor
                )
                pillBgColor = getColor(
                    R.styleable.CustomBottomNavigationView_pillBackgroundColor,
                    pillBgColor
                )
                iconTint = getColor(
                    R.styleable.CustomBottomNavigationView_navIconTint,
                    0
                ).takeIf { it != 0 }
                menuResId = getResourceId(
                    R.styleable.CustomBottomNavigationView_menu,
                    0
                )
                defaultSelected = getInteger(
                    R.styleable.CustomBottomNavigationView_defaultSelected,
                    1
                )
                // Fix: Use default value if attribute is not provided
                cornerRadius = getDimensionPixelSize(
                    R.styleable.CustomBottomNavigationView_pillCornerRadius,
                    defaultCornerRadiusPx
                ).toFloat()
            }
        } else {
            // Set default if no attrs provided at all
            cornerRadius = defaultCornerRadiusPx.toFloat()
        }

        // Apply pill background customization
        val pill = findViewById<LinearLayout>(R.id.pill_container)
        val pillDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(pillBgColor)
            setStroke(pillStrokeWidth, pillStrokeColor)
            cornerRadius = this@CustomBottomNavigationView.cornerRadius
        }
        pill.background = pillDrawable

        // Load menu.xml if provided
        if (menuResId != 0) {
            val popupMenu = android.widget.PopupMenu(context, this)
            val menu = popupMenu.menu
            MenuInflater(context).inflate(menuResId, menu)
            for (i in 0 until items.size) {
                if (i < menu.size) {
                    val menuItem = menu[i]
                    icons[i].setImageDrawable(menuItem.icon)
                    labels[i].text = menuItem.title
                    items[i].visibility = View.VISIBLE
                } else {
                    items[i].visibility = View.GONE
                }
            }
        }

        // Apply icon tint if set
        iconTint?.let { tintColor ->
            for (icon in icons) {
                icon.imageTintList = ColorStateList.valueOf(tintColor)
            }
        }

        items.forEachIndexed { index, layout ->
            layout.setOnClickListener {
                setSelected(index)
                onItemSelected?.invoke(index)
            }
        }

        post { setSelected(defaultSelected) }
    }

    // Replaced selection logic: selected item hides its icon (dot replaces it),
    // unselected items show only the icon.

    fun setSelected(index: Int) {
        if (index == selectedIndex) return
        selectedIndex = index

        for (i in items.indices) {

            val item = items[i]
            val icon = icons[i]
            val label = labels[i]
            val dot = dots[i]
            val active = (i == index)

            // Cancel any animations
            item.animate().cancel()
            icon.animate().cancel()
            label.animate().cancel()
            dot.animate().cancel()

            if (active) {

                // Scale pop
                item.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200)
                    .start()

                // ICON hide with 60f slide
                if (icon.isVisible) {
                    icon.alpha = 1f
                    icon.translationY = 0f
                    icon.animate()
                        .alpha(0f)
                        .translationY(-60f)   // ★ 60f here
                        .setDuration(200)
                        .withEndAction { icon.visibility = GONE }
                        .start()
                }

                // LABEL appear with 60f slide
                label.visibility = VISIBLE
                label.alpha = 0f
                label.translationY = 60f      // ★ 60f here
                label.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(260)
                    .start()

                // DOT pop
                dot.visibility = VISIBLE
                dot.scaleX = 0f
                dot.scaleY = 0f
                dot.alpha = 0f
                dot.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(220)
                    .start()

            } else {

                // Scale down
                item.animate()
                    .scaleX(0.90f)
                    .scaleY(0.90f)
                    .setDuration(200)
                    .start()

                // LABEL hide with 60f slide
                if (label.isVisible) {
                    label.animate()
                        .alpha(0f)
                        .translationY(60f)   // ★ 60f here
                        .setDuration(200)
                        .withEndAction {
                            label.visibility = GONE
                            label.alpha = 0f
                        }
                        .start()
                }

                // ICON return with 60f slide
                if (icon.visibility != VISIBLE) {
                    icon.visibility = VISIBLE
                    icon.alpha = 0f
                    icon.translationY = -60f    // ★ 60f here
                }
                icon.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(220)
                    .start()

                // DOT hide (no GONE layout jump)
                dot.animate()
                    .alpha(0f)
                    .scaleX(0.3f)
                    .scaleY(0.3f)
                    .setDuration(150)
                    .withEndAction {
                        dot.visibility = GONE
                        dot.alpha = 0f
                        dot.scaleX = 0.3f
                        dot.scaleY = 0.3f
                    }
                    .start()
            }
        }
    }

    fun setLabel(index: Int, text: String) {
        if (index in labels.indices) labels[index].text = text
    }

    fun setIcon(index: Int, resId: Int) {
        if (index in icons.indices) icons[index].setImageResource(resId)
    }
}
