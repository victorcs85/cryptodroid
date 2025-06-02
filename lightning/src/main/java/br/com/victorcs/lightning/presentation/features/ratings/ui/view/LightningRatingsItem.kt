package br.com.victorcs.lightning.presentation.features.ratings.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.victorcs.core.extensions.orZero
import br.com.victorcs.core.theme.LightBackground
import br.com.victorcs.lightning.R
import br.com.victorcs.lightning.domain.model.Country
import br.com.victorcs.lightning.domain.model.Node
import java.text.NumberFormat

@Composable
fun LightningRatingsItem(node: Node, modifier: Modifier = Modifier) {
    val location = listOfNotNull(
        node.city?.getLocalizedName(),
        node.country?.getLocalizedName(),
    ).joinToString(", ")

    val formattedCapacity = NumberFormat.getNumberInstance().format(node.capacity ?: 0.0)

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = LightBackground),
    ) {
        val channelString = stringResource(R.string.channels, node.channels.orZero())
        val capacityString = stringResource(R.string.capacity_sats, formattedCapacity)
        val localizationString = stringResource(R.string.localization, location)
        val updatedAtString = stringResource(R.string.updated_at, node.updatedAt.orEmpty())

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = node.alias.orEmpty(),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = node.publicKey,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = channelString,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = capacityString,
                style = MaterialTheme.typography.bodyMedium,
            )
            if (location.isNotBlank()) {
                Text(
                    text = localizationString,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Text(
                text = updatedAtString,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightningRatingsItemPreview() {
    LightningRatingsItem(
        node = Node(
            publicKey = "03864ef025fde8fb587d989186ce6a4a186895ee44a926bfc370e2c366597a3f8f",
            alias = "ACINQ",
            channels = 2908,
            capacity = 36010516297.0,
            firstSeen = "1522941222",
            updatedAt = "1661274935",
            city = null,
            country = Country(null, "United States", null, null, null, "EUA", null, null),
            isoCode = null,
            subdivision = null,
        ),
    )
}
