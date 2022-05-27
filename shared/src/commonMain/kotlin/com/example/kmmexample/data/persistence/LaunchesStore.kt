package com.example.kmmexample.data.persistence

import com.example.kmmexample.data.database.LaunchesQueries
import com.example.kmmexample.data.database.RocketQueries
import com.example.kmmexample.data.models.Links
import com.example.kmmexample.data.models.Rocket
import com.example.kmmexample.data.models.RocketLaunch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface LaunchesStore {
    suspend fun saveAllLaunches(launches: List<RocketLaunch>)
    suspend fun getAllLaunches(): List<RocketLaunch>
}

class LaunchesPersistence(
    private val launchesQueries: LaunchesQueries,
    private val rocketQueries: RocketQueries
): LaunchesStore {

    private fun removeAllLaunches() {
        launchesQueries.removeAllLaunches()
    }

    private fun removeAllRockets() {
        rocketQueries.removeAllRockets()
    }

    private fun selectRocketById(id: String) =
        rocketQueries.selectRocketById(id).executeAsOneOrNull()

    override suspend fun saveAllLaunches(launches: List<RocketLaunch>) {
        withContext(Dispatchers.Unconfined) {
            launchesQueries.transaction {
                removeAllLaunches()
                launches.forEach { launch ->
                    val rocket = selectRocketById(launch.rocket.id)
                    if (rocket == null) {
                        insertRocket(launch)
                    }
                    insertLaunch(launch)
                }
            }
        }
    }

    override suspend fun getAllLaunches() = withContext(Dispatchers.Unconfined) {
        launchesQueries
            .selectAllLaunchesInfo(::mapLaunchSelecting)
            .executeAsList()
    }


    private fun insertRocket(launch: RocketLaunch) {
        rocketQueries.insertRocket(
            id = launch.rocket.id,
            name = launch.rocket.name,
            type = launch.rocket.type
        )
    }

    private fun insertLaunch(launch: RocketLaunch) {
        launchesQueries.insertLaunch(
            flightNumber = launch.flightNumber.toLong(),
            missionName = launch.missionName,
            launchYear = launch.launchYear,
            rocketId = launch.rocket.id,
            details = launch.details,
            launchSuccess = launch.launchSuccess ?: false,
            launchDateUTC = launch.launchDateUTC,
            missionPatchUrl = launch.links.missionPatchUrl,
            articleUrl = launch.links.articleUrl
        )
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        launchYear: Int,
        rocketId: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        missionPatchUrl: String?,
        articleUrl: String?,
        rocket_id: String?,
        name: String?,
        type: String?
    ) = RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            launchYear = launchYear,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            rocket = Rocket(
                id = rocketId,
                name = name!!,
                type = type!!
            ),
            links = Links(
                missionPatchUrl = missionPatchUrl,
                articleUrl = articleUrl
            )
        )
}