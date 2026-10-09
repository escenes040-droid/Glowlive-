package com.glowlive.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Teal = Color(0xFF18C8B9)
private val Ink = Color(0xFF18272B)
private val Muted = Color(0xFF748184)
private val Pale = Color(0xFFF4F7F8)
private val Gold = Color(0xFFFFB547)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GlowLiveApp() }
    }
}

@Composable
fun GlowLiveApp() {
    var tab by remember { mutableStateOf("Me") }
    var coins by remember { mutableIntStateOf(4) }
    var points by remember { mutableIntStateOf(3000) }
    var tasksDone by remember { mutableStateOf(setOf<String>()) }
    var message by remember { mutableStateOf("") }
    var sentMessages by remember { mutableStateOf(listOf("Welcome to GlowLive!")) }
    var showAdmin by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = lightColorScheme(primary = Teal, background = Pale, surface = Color.White)
    ) {
        Scaffold(
            containerColor = Pale,
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    val items = listOf("Rooms", "Moments", "Messages", "Me")
                    val icons = listOf(Icons.Default.Videocam, Icons.Default.Explore, Icons.Default.ChatBubble, Icons.Default.Person)
                    items.forEachIndexed { i, label ->
                        NavigationBarItem(
                            selected = tab == label,
                            onClick = { tab = label },
                            icon = { Icon(icons[i], contentDescription = label) },
                            label = { Text(label) },
                            colors = NavigationBarItemDefaults.colors(selectedIconColor = Teal, selectedTextColor = Teal)
                        )
                    }
                }
            }
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding)) {
                when (tab) {
                    "Me" -> ProfileScreen(
                        coins, points, onNavigate = { tab = it },
                        onAddCoins = { coins += 10 },
                        onTask = { tab = "Tasks" },
                        onAdmin = { showAdmin = true }
                    )
                    "Rooms" -> RoomsScreen(onJoin = { tab = "Messages" })
                    "Moments" -> TasksScreen(tasksDone, onClaim = { task ->
                        if (task !in tasksDone) {
                            tasksDone = tasksDone + task
                            coins += 2
                            points += 25
                        }
                    })
                    "Messages" -> MessagesScreen(
                        sentMessages, message, onMessageChange = { message = it },
                        onSend = {
                            if (message.isNotBlank()) {
                                sentMessages = sentMessages + message.trim()
                                message = ""
                            }
                        }
                    )
                    "Tasks" -> TasksScreen(tasksDone, onClaim = { task ->
                        if (task !in tasksDone) {
                            tasksDone = tasksDone + task
                            coins += 2
                            points += 25
                        }
                    })
                    "Wallet" -> WalletScreen(coins, points, onRecharge = { coins += 10 })
                    "Host Center" -> HostScreen()
                    "Agency" -> AgencyScreen()
                    "Admin" -> AdminScreen()
                    else -> ProfileScreen(coins, points, onNavigate = { tab = it }, onAddCoins = { coins += 10 }, onTask = { tab = "Tasks" }, onAdmin = { showAdmin = true })
                }
            }
            if (showAdmin) {
                AlertDialog(
                    onDismissRequest = { showAdmin = false },
                    title = { Text("Admin access") },
                    text = { Text("Demo dashboard only. Production admin access must use a secure server-side role and login.") },
                    confirmButton = {
                        TextButton(onClick = { showAdmin = false; tab = "Admin" }) { Text("Open demo") }
                    },
                    dismissButton = { TextButton(onClick = { showAdmin = false }) { Text("Cancel") }
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(coins: Int, points: Int, onNavigate: (String) -> Unit, onAddCoins: () -> Unit, onTask: () -> Unit, onAdmin: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 18.dp)
    ) {
        item {
            Box(Modifier.fillMaxWidth().background(Brush.linearGradient(listOf(Color(0xFFB8F5E8), Color(0xFFE7FFD1), Color(0xFFEAFBFF)))).padding(20.dp)) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(92.dp).background(Color.White, CircleShape), contentAlignment = Alignment.Center) {
                            Text("✨", fontSize = 40.sp)
                        }
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Text("GlowLive", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                            Text("@kirubel_creator", color = Ink)
                            Spacer(Modifier.height(6.dp))
                            Surface(color = Teal, shape = RoundedCornerShape(12.dp)) {
                                Text(" ✦ CREATOR ", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 11.sp)
                            }
                            Text("ID: 3864135  ▢", color = Ink, modifier = Modifier.padding(top = 6.dp), fontSize = 13.sp)
                        }
                        Icon(Icons.Default.ChevronRight, null, tint = Ink)
                    }
                    Spacer(Modifier.height(20.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        Stat("5", "Followed")
                        Stat("7", "Following")
                        Stat("0", "Friends")
                    }
                }
            }
        }
        item {
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column {
                    Row(Modifier.fillMaxWidth().background(Brush.horizontalGradient(listOf(Color(0xFF5D3C20), Color(0xFF2D2017)))).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("♛", fontSize = 30.sp, color = Gold)
                        Column(Modifier.weight(1f).padding(start = 10.dp)) {
                            Text("Glow VIP Club", color = Color(0xFFFFE7B5), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Daily perks and creator rewards", color = Color(0xFFFFE7B5), fontSize = 12.sp)
                        }
                        TextButton(onClick = { onNavigate("Wallet") }) { Text("Explore", color = Color(0xFFFFD08A)) }
                    }
                    Row(Modifier.fillMaxWidth().padding(18.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Coins", color = Muted)
                            Text("🟡 $coins", color = Gold, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text("Points", color = Muted)
                            Text("ⓗ $points", color = Teal, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        item {
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(14.dp)) {
                    val actions = listOf(
                        Triple("Recharge", "💳", "Wallet"), Triple("Store", "🛍️", "Wallet"),
                        Triple("Invitation", "💌", "Agency"), Triple("Backpack", "🎒", "Wallet"),
                        Triple("Lucky Island", "🏝️", "Tasks"), Triple("Level", "⭐", "Host Center"),
                        Triple("Task", "🗓️", "Tasks"), Triple("Badge", "🏅", "Host Center"),
                        Triple("Host Center", "🎙️", "Host Center"), Triple("Agency", "💼", "Agency"),
                        Triple("Coin Seller", "💰", "Wallet"), Triple("Support", "🎧", "Messages")
                    )
                    actions.chunked(4).forEach { chunk ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            chunk.forEach { (label, emoji, destination) ->
                                Column(Modifier.weight(1f).clickable { onNavigate(destination) }.padding(vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(emoji, fontSize = 26.sp)
                                    Spacer(Modifier.height(6.dp))
                                    Text(label, fontSize = 11.sp, color = Ink, maxLines = 1)
                                }
                            }
                            repeat(4 - chunk.size) { Spacer(Modifier.weight(1f)) }
                        }
                    }
                }
            }
        }
        item {
            Card(Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF8D1720))) {
                Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("YOUR SPACE. YOUR PEOPLE.", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Text("Keep your account safe", color = Color.White)
                    }
                    Text("🛡️", fontSize = 42.sp)
                }
            }
        }
        item {
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Creator tools", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                        TextButton(onClick = onAdmin) { Text("Admin demo") }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedButton(onClick = { onNavigate("Host Center") }, modifier = Modifier.weight(1f)) { Text("Host Center") }
                        OutlinedButton(onClick = { onNavigate("Agency") }, modifier = Modifier.weight(1f)) { Text("Agency") }
                    }
                }
            }
        }
    }
}

@Composable
fun Stat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Ink)
        Text(label, color = Ink)
    }
}

@Composable
fun PageHeader(title: String, subtitle: String) {
    Column(Modifier.fillMaxWidth().background(Brush.horizontalGradient(listOf(Color(0xFFB8F5E8), Color(0xFFE7FFD1)))).padding(20.dp)) {
        Text(title, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
        Text(subtitle, color = Muted)
    }
}

@Composable
fun RoomsScreen(onJoin: () -> Unit) {
    val rooms = listOf(
        Triple("Chill & Chat", "MimiStar", "128 watching"),
        Triple("Music Lounge 🎵", "DJ_Nova", "86 watching"),
        Triple("Meet New Friends", "LunaLive", "52 watching"),
        Triple("Amharic Hangout 🇪🇹", "HabeshaVibes", "41 watching")
    )
    LazyColumn(contentPadding = PaddingValues(bottom = 20.dp)) {
        item { PageHeader("Live rooms", "Find your people and join the conversation") }
        item { Text("LIVE NOW", color = Teal, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp)) }
        items(rooms) { room ->
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp).clickable { onJoin() }, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(58.dp).background(Brush.linearGradient(listOf(Teal, Color(0xFFB4F4C8))), CircleShape), contentAlignment = Alignment.Center) { Text("🎙️", fontSize = 26.sp) }
                    Column(Modifier.weight(1f).padding(start = 12.dp)) {
                        Text(room.first, fontWeight = FontWeight.Bold, color = Ink)
                        Text("Host: ${room.second}", color = Muted, fontSize = 13.sp)
                        Text(room.third, color = Teal, fontSize = 12.sp)
                    }
                    Button(onClick = onJoin, shape = RoundedCornerShape(14.dp)) { Text("Join") }
                }
            }
        }
        item { Text("Demo rooms are sample data. Real audio/video streaming needs a streaming provider and backend.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(16.dp)) }
    }
}

@Composable
fun TasksScreen(done: Set<String>, onClaim: (String) -> Unit) {
    val tasks = listOf(
        Triple("Visit the app", "Open GlowLive today", " +2 coins · +25 points"),
        Triple("Say hello", "Send a friendly message", " +2 coins · +25 points"),
        Triple("Explore live rooms", "Check out a room", " +2 coins · +25 points"),
        Triple("Creator profile", "Review your creator profile", " +2 coins · +25 points")
    )
    LazyColumn(contentPadding = PaddingValues(bottom = 20.dp)) {
        item { PageHeader("Tasks & rewards", "Small steps, glowing rewards") }
        item { Text("DAILY MISSIONS", fontWeight = FontWeight.Bold, color = Teal, modifier = Modifier.padding(16.dp)) }
        items(tasks) { task ->
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(if (task.first in done) "✅" else "🎁", fontSize = 28.sp)
                    Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                        Text(task.first, fontWeight = FontWeight.Bold, color = Ink)
                        Text(task.second, color = Muted, fontSize = 13.sp)
                        Text(task.third, color = Teal, fontSize = 12.sp)
                    }
                    Button(onClick = { onClaim(task.first) }, enabled = task.first !in done) {
                        Text(if (task.first in done) "Done" else "Claim")
                    }
                }
            }
        }
        item { Text("Rewards in this prototype are demo points only and have no cash value.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(16.dp)) }
    }
}

@Composable
fun MessagesScreen(messages: List<String>, message: String, onMessageChange: (String) -> Unit, onSend: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader("Messages", "Your conversations")
        LazyColumn(Modifier.weight(1f).fillMaxWidth().padding(12.dp)) {
            items(messages) { msg ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Surface(color = Color(0xFFDDF9F2), shape = RoundedCornerShape(18.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(msg, modifier = Modifier.padding(12.dp), color = Ink)
                    }
                }
            }
        }
        Row(Modifier.fillMaxWidth().background(Color.White).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = message, onValueChange = onMessageChange, modifier = Modifier.weight(1f), placeholder = { Text("Write a message…") }, shape = RoundedCornerShape(20.dp), singleLine = true)
            IconButton(onClick = onSend) { Icon(Icons.Default.Send, "Send", tint = Teal) }
        }
    }
}

@Composable
fun WalletScreen(coins: Int, points: Int, onRecharge: () -> Unit) {
    LazyColumn(contentPadding = PaddingValues(bottom = 24.dp)) {
        item { PageHeader("Wallet", "Coins, points and payout settings") }
        item {
            Card(Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("Available coins", color = Muted)
                    Text("🟡 $coins", color = Gold, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Text("Creator points", color = Muted)
                    Text("$points pts", color = Teal, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(14.dp))
                    Button(onClick = onRecharge, modifier = Modifier.fillMaxWidth()) { Text("Add 10 demo coins") }
                }
            }
        }
        item {
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(18.dp)) {
                    Text("Withdrawals", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("No real-money payments are connected in this demo.", color = Muted, modifier = Modifier.padding(top = 6.dp))
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) { Text("Connect payout provider in production") }
                }
            }
        }
        item { Text("Never add real funds until secure payment processing, fraud controls, and local legal requirements are implemented.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(16.dp)) }
    }
}

@Composable
fun HostScreen() {
    LazyColumn {
        item { PageHeader("Host Center", "Tools for creators and live hosts") }
        item {
            listOf("Creator status" to "New creator", "Weekly activity" to "0 / 5 sessions", "Audience" to "No live analytics yet", "Host guidelines" to "Be respectful and keep your room safe").forEach { row ->
                Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(row.first, fontWeight = FontWeight.Bold, color = Ink)
                        Text(row.second, color = Muted)
                    }
                }
            }
        }
    }
}

@Composable
fun AgencyScreen() {
    LazyColumn {
        item { PageHeader("Agency", "Manage hosts and team activity") }
        item {
            Card(Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(18.dp)) {
                    Text("Agency overview", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Text("Hosts linked: 0")
                    Text("Pending applications: 0")
                    Text("This is a local demo. Real agency membership and commissions require server verification.")
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Apply to create an agency") }
                }
            }
        }
    }
}

@Composable
fun AdminScreen() {
    LazyColumn {
        item { PageHeader("Admin dashboard", "Prototype operations overview") }
        item {
            val stats = listOf("Registered users" to "1 (sample)", "Active rooms" to "4 (sample)", "Pending withdrawals" to "0", "Reports to review" to "0")
            stats.forEach { stat ->
                Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) { Text(stat.first, color = Muted); Text(stat.second, fontWeight = FontWeight.Bold, fontSize = 20.sp) }
                        Icon(Icons.Default.ChevronRight, null, tint = Teal)
                    }
                }
            }
        }
        item { Text("Security note: production admin actions must require verified admin roles on a backend, not just an app screen.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(16.dp)) }
    }
}
