package com.example.kmmexample.data.repository

import com.example.kmmexample.data.database.LaunchesQueries
import com.example.kmmexample.data.database.RocketQueries
import com.example.kmmexample.data.models.Links
import com.example.kmmexample.data.models.Rocket
import com.example.kmmexample.data.models.RocketLaunch
import com.example.kmmexample.data.services.LaunchesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LaunchesRepository: KoinComponent {

    private val service: LaunchesService by inject()
    private val launchesQueries: LaunchesQueries by inject()
    private val rocketQueries: RocketQueries by inject()

    @Throws(Exception::class)
    suspend fun getLaunches(): List<RocketLaunch> = withContext(Dispatchers.Unconfined) {
        try {
            service.getLaunches().also {
                createLaunches(it)
            }
            getAllLaunches()
        } catch (e: Exception) {
            e.printStackTrace()
            getAllLaunches()
        }
    }

    private suspend fun createLaunches(launches: List<RocketLaunch>) {
        return withContext(Dispatchers.Unconfined) {
            launchesQueries.transaction {
                launchesQueries.removeAllLaunches()
                rocketQueries.removeAllRockets()

                launches.forEach { launch ->
                    val rocket =
                        rocketQueries.selectRocketById(launch.rocket.id).executeAsOneOrNull()
                    if (rocket == null) {
                        insertRocket(launch)
                    }
                    insertLaunch(launch)
                }
            }
        }
    }

    private suspend fun getAllLaunches() = withContext(Dispatchers.Unconfined) {
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
    ) =
        RocketLaunch(
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