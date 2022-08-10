/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.NetworkAsteroidContainer
import com.udacity.asteroidradar.domain.Asteroid



@Entity
data class DatabaseAsteroid constructor(
        @PrimaryKey
        val id: String,
        val name: String,
        val absolute_magnitude: String,
        val estimated_diameter_max : Double,
        val is_potentially_hazardous_asteroid: Boolean,
        val close_approach_data: String,
        val kilometers_per_second : String,
        val miss_distance_astronomical : String)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
                id = it.id.toLong(),
                codename = it.name,
                closeApproachDate = it.close_approach_data,
                absoluteMagnitude = it.absolute_magnitude.toDouble(),
                estimatedDiameter = it.estimated_diameter_max,
                relativeVelocity =  it.kilometers_per_second.toDouble(),
                distanceFromEarth = it.miss_distance_astronomical.toDouble(),
                isPotentiallyHazardous = it.is_potentially_hazardous_asteroid
            )
    }
}
fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {
        return map {
                DatabaseAsteroid(
                        id = it.id.toString(),
                        name = it.codename,
                        absolute_magnitude = it.absoluteMagnitude.toString(),
                        estimated_diameter_max = it.estimatedDiameter,
                        is_potentially_hazardous_asteroid = it.isPotentiallyHazardous,
                        close_approach_data = it.closeApproachDate,
                        kilometers_per_second = it.relativeVelocity.toString(),
                        miss_distance_astronomical = it.distanceFromEarth.toString()
                )
        }.toTypedArray()
}



