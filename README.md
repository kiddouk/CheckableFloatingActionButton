# Checkable Floating Button #

A simple Floating Action Button that supports states. You can configure the following :
* elevation
* elevation when in state "checked"
* asset to display when checked/unchecked

The button also saves its state when the screen is getting rotated so you dont have to handle that yourself.

Of course, the button supports callbacks when the button is checked/unchecked.


## How to use ##

```xml
<io.errorlab.widget.LayeredCheckableFloatingActionButton
     android:id="@+id/fab"
     android:layout_height="wrap_content"
     android:layout_width="wrap_content"
     android:src="@drawable/fab_switch"
     android:layout_margin="16dp"
     android:elevation="8dp"
     app:pressed_elevation="0dp"
     android:addStatesFromChildren="true"
     app:borderWidth="0dp"
     app:layout_anchor="@id/appbar"
     app:layout_anchorGravity="bottom|right|end"
     app:backgroundTint="@color/fab"
     app:layout_behavior="@string/appbar_scrolling_view_behavior"
     />
```

The property addStateFronChildren is mandatory for now, the rest is self explanatory.
The drawable set in src should be a StateDrawable defined like so :

```xml
<selector
   xmlns:android="http://schemas.android.com/apk/res/android"
   android:exitFadeDuration="@android:integer/config_mediumAnimTime"
   android:enterFadeDuration="@android:integer/config_mediumAnimTime"
   android:constantSize="true"
   
   >
  
    <item android:state_checked="true"
          android:drawable="@drawable/ic_liked_fab_24dp" />
    
    <item android:drawable="@drawable/ic_like_fab_24dp" />
    
</selector>
```



Want something more in this ? Send me a pull request !


