package tw.edu.pu.csim.peichi.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.csim.peichi.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
    // 1. 修正 remember 拼字錯誤，並正確宣告在 Composable 函式內部
    var canvasWidth by remember { mutableStateOf(0f) }
    var canvasHeight by remember { mutableStateOf(0f) }

    // 2. 修正圖片載入語法，移除多餘的 id =
    val minionsImage: ImageBitmap = ImageBitmap.imageResource(R.drawable.minions)

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // 3. 修正 size.width/height 擺放位置，必須寫在 Canvas 內部（DrawScope）
            canvasWidth = size.width
            canvasHeight = size.height

            drawCircle(
                Color.Magenta,
                100f,
                Offset(100f, 100f)
            )
            drawRect(
                Color.Blue,
                Offset(300f, 200f),
                Size(200f, 200f),
                style = Stroke(width = 5f) // 使用 style 控制外框樣式
            )
            drawLine(
                color = Color.Red,
                start = Offset(x = 200f, y = 800f),
                end = Offset(x = size.width, y = size.height),
                strokeWidth = 10f
            )

            // 取得小小兵圖檔的寬度與高度
            val minionsWidth = minionsImage.width
            val minionsHeight = minionsImage.height

            // 置中公式：（畫布寬高 - 圖片寬高）/ 2
            val offsetX = (size.width - minionsWidth) / 2f
            val offsetY = (size.height - minionsHeight) / 2f

            // 將畫布起點移至置中位置後畫圖
            translate(left = offsetX, top = offsetY) {
                drawImage(minionsImage)
            }
        }

        // 顯示畫布寬高的文字
        Text(text = "畫布寬度: $canvasWidth, 長度: $canvasHeight")
    }
}