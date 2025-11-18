# smooth-bottom-navigation

A lightweight, fully customizable, smooth animated bottom navigation view for Android — without Compose.

Designed for modern apps that need **premium animations**, **zero layout jank**, and **full XML customization**.

---

## 🚀 Overview

* Smooth animated navigation with icon → label transition
* Selected item shows label + animated bottom dot
* Unselected items show only icons
* Equal-width items (supports **2–5 items**)
* Highly configurable via XML attributes
* Respects system navigation bar insets automatically
* Lightweight — no extra dependencies

---

## 📦 Installation

Copy the following into your project:

1. `CustomBottomNavigationView.kt` → `app/src/main/java/.../`
2. `view_custom_bottom_nav.xml` → `res/layout/`
3. Required drawables → `res/drawable/`
4. `attrs.xml`, `colors.xml`, `dimens.xml` → `res/values/`
5. A menu resource → `res/menu/`

That’s it — no Gradle changes required.

---

## 🧩 XML Usage

```xml
<com.example.custombottomnavigation.CustomBottomNavigationView
    android:id="@+id/custom_nav"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingVertical="4dp"
    android:paddingHorizontal="16dp"
    android:layout_marginBottom="16dp"
    app:menu="@menu/bottom_nav_menu"
    app:defaultSelected="0"
    app:pillStrokeWidth="0.5dp"
    app:pillStrokeColor="?colorOutline"
    app:pillBackgroundColor="@android:color/white"
    app:pillCornerRadius="36dp"
    app:navIconTint="@color/icon_default"
    app:layout_constraintBottom_toBottomOf="parent" />
```

---

## 📜 Menu Example (`res/menu/bottom_nav_menu.xml`)

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/nav_item1" android:icon="@android:drawable/ic_menu_view" android:title="item1" />
    <item android:id="@+id/nav_item2" android:icon="@android:drawable/ic_menu_camera" android:title="item2" />
    <item android:id="@+id/nav_item3" android:icon="@android:drawable/ic_menu_agenda" android:title="item3" />
    <item android:id="@+id/nav_item4" android:icon="@android:drawable/ic_menu_manage" android:title="item4" />
</menu>
```

---

## 🎛️ Custom XML Attributes

| Attribute             | Type      | Description              | Default |
|-----------------------|-----------|--------------------------|---------|
| `menu`                | reference | Menu resource with items | –       |
| `defaultSelected`     | integer   | Default selected index   | 1       |
| `pillStrokeWidth`     | dimension | Pill stroke width        | 2dp     |
| `pillStrokeColor`     | color     | Pill border color        | #0B78FF |
| `pillBackgroundColor` | color     | Pill background          | #FFFFFF |
| `pillCornerRadius`    | dimension | Rounded pill radius      | 36dp    |
---

## 🧑‍💻 Programmatic Usage

```kotlin
binding.customNav.onItemSelected = { index ->
    when (index) {
        0 -> loadFragment(HomeFragment())
        1 -> loadFragment(SearchFragment())
        2 -> loadFragment(UploadFragment())
        3 -> loadFragment(ProfileFragment())
    }
}

// Change selected item
binding.customNav.setSelected(0)

// Update dynamically
binding.customNav.setIcon(0, R.drawable.custom_icon)
binding.customNav.setLabel(0, "Custom Label")
```

---

## 📱 System Insets (Navigation Bar)

Already handled internally:

```kotlin
ViewCompat.setOnApplyWindowInsetsListener(binding.customNav) { v, insets ->
    val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
    v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, v.paddingBottom + sys.bottom)
    insets
}
```

---

## ✨ Features

* Smooth icon → label → dot animation
* Zero layout-jank thanks to fixed-height structure
* Full customization via XML
* Handles system insets
* Dynamic menu loading
* Supports 2–5 navigation items
* Lightweight and dependency-free

---

## 📁 Required Resources

### Drawables

* `bg_pill.xml` → pill background
* `circle_blue.xml` → animated dot

### `colors.xml`

```xml
<color name="default_pill_stroke_color">#0B78FF</color>
<color name="default_pill_bg_color">#FFFFFF</color>
```

### `dimens.xml`

```xml
<dimen name="default_pill_stroke_width">2dp</dimen>
```

---

## 🧠 Tips & Best Practices

* Use `app:menu` for clean configuration
* Use `app:defaultSelected` to set initial tab
* Customize `app:pillCornerRadius` for rounded look
* Set `app:pillStrokeWidth="0dp"` for a flat pill
* Add more items by extending XML + Kotlin array setup

---
sHafQatU#837
## 🛠 Troubleshooting

| Problem                       | Fix                                 |
|-------------------------------|-------------------------------------|
| Icons missing                 | Check menu item drawable references |
| Stroke invisible              | Increase `pillStrokeWidth`          |
| Default selection not working | Ensure index is 0-based             |

---

## 🧩 Example Activity

```kotlin
binding.customNav.onItemSelected = { index ->
    val fragment = when (index) {
        0 -> HomeFragment()
        1 -> SearchFragment()
        2 -> UploadFragment()
        3 -> ProfileFragment()
        else -> HomeFragment()
    }

    supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragment)
        .commit()
}
```

---

## 📝 Summary

`smooth-bottom-navigation` is a lightweight, fully customizable, smooth animated bottom navigation component for Android. Ideal for modern apps that need unique, fluid navigation without relying on Material3 BottomNavigationView.

---

For advanced tweaks or contributions, explore `CustomBottomNavigationView.kt` in the source code.
