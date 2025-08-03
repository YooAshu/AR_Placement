package com.example.arplacement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position

class ARActivity : AppCompatActivity() {

    private lateinit var sceneView: ArSceneView
    private lateinit var placeButton: ExtendedFloatingActionButton
    private lateinit var modelNode: ArModelNode
    private lateinit var drillName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        drillName = intent.getStringExtra("drillName") ?: "Drill 1"

        // Initialize ARSceneView
        sceneView = findViewById(R.id.sceneView)
        sceneView.lightEstimationMode = Config.LightEstimationMode.DISABLED

        placeButton = findViewById(R.id.place)

        // Create and add the model node
        modelNode = ArModelNode(sceneView.engine, PlacementMode.BEST_AVAILABLE).apply {
            loadModelGlbAsync(
                glbFileLocation = when (drillName) {
                    "Drill 1" -> "models/traffic_cone_game_ready.glb"
                    "Drill 2" -> "models/floating_cube.glb"
                    "Drill 3" -> "models/football__soccer_ball.glb"
                    else -> "models/traffic_cone_game_ready.glb"
                },
                scaleToUnits = 1f,
                centerOrigin = Position(0f)
            ) {
                // Optional: show plane renderer after model loads
                sceneView.planeRenderer.isVisible = true
            }

            // Hide place button once anchored
            onAnchorChanged = {
                placeButton.isGone = it != null
            }
        }

        // Add to scene
        sceneView.addChild(modelNode)

        // Tap to anchor
        placeButton.setOnClickListener {
            modelNode.anchor()
            sceneView.planeRenderer.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sceneView.destroy()
    }
}
