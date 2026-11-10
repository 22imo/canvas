
package tw.edu.pu.csim.peichi.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import kotlinx.coroutines.delay
import tw.edu.pu.csim.peichi.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CanvasTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CanvasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CanvasScreen(modifier: Modifier = Modifier) {

    var canvasWidth by remember {
        mutableFloatStateOf(0f)
    }

    var canvasHeight by remember {
        mutableFloatStateOf(0f)
    }

    var minionsX by remember {
        mutableFloatStateOf(0f)
    }

    var minionsY by remember {
        mutableFloatStateOf(0f)
    }

    var gameOver by remember {
        mutableStateOf(true)
    }

    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(R.drawable.minions)

    // 控制小小兵移動
    LaunchedEffect(gameOver) {

        while (!gameOver) {

            delay(30L)

            // 往右下角移動
            minionsX += 2f
            minionsY += 5f

            // 碰到右邊或底端，回到左上角
            if (minionsX + minionsImage.width >= canvasWidth ||
                minionsY + minionsImage.height >= canvasHeight
            ) {
                minionsX = 0f
                minionsY = 0f
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            // 取得畫布大小
            canvasWidth = size.width
            canvasHeight = size.height

            // 畫圓形
            drawCircle(
                color = Color.Magenta,
                radius = 100f,
                center = Offset(300f, 800f)
            )

            // 畫矩形
            drawRect(
                color = Color.Blue,
                topLeft = Offset(300f, 200f),
                size = Size(200f, 200f),
                style = Stroke(width = 5f)
            )

            // 畫線
            drawLine(
                color = Color.Red,
                start = Offset(200f, 800f),
                end = Offset(size.width, size.height),
                strokeWidth = 10f
            )

            // 繪製小小兵
            translate(
                left = minionsX,
                top = minionsY
            ) {
                drawImage(minionsImage)
            }
        }

        // 顯示畫布資訊及控制按鈕
        Column {

            Text(
                text = "畫布寬度: $canvasWidth, 長度: $canvasHeight"
            )

            Button(
                onClick = {
                    gameOver = !gameOver
                }
            ) {

                if (gameOver) {
                    Text("開始遊戲")
                } else {
                    Text("停止遊戲")
                }
            }
        }
    }
}
