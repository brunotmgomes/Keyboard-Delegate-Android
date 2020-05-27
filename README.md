# Keyboard-Delegate-Android

Android only supports changing input type by Activities by default. 
This library is a simple way to change input types in fragments. 

It will dettach and attach input type preference by lifecycle.

## How to Install:
add to gradle:
```
implementation("com.brunotmgomes:keyboard-delegate-android:0.1.2")
```

## How to use:

Initialize in activity
``` kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KeyboardDelegate.initialize(this)
    }
```

Choose Input type in fragmet
``` kotlin
    // Remember to get instance when view lifecycle is available
    private val keyboardDelegate by lazy { KeyboardDelegate.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keyboardDelegate.setInputModeOverride(viewLifecycleOwner, KeyboardDelegate.ADJUST_RESIZE)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_global_adjustPanFragment)
        }
    }
```

Done.

