import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterAsDudeComponent} from './components/register-as-dude/register-as-dude.component';
import {RegisterAsFitnessProviderComponent} from './components/register-as-fitness-provider/register-as-fitness-provider.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {DudeProfileComponent} from './components/dude-profile/dude-profile.component';
import {BrosComponent} from './components/bros/bros.component';
import {CreateComponent} from './components/create/create.component';
import {FindComponent} from './components/find/find.component';
import {HistoryComponent} from './components/history/history.component';
import {MyContentComponent} from './components/my-content/my-content.component';
import {FavouritesComponent} from './components/favourites/favourites.component';
import {EditProfileComponent} from './components/edit-profile/edit-profile.component';
import {FitnessProviderProfileComponent} from './components/fitness-provider-profile/fitness-provider-profile.component';
import {LoginAsFitnessProviderComponent } from './components/login-as-fitness-provider/login-as-fitness-provider.component';
import {RoleGuard} from './guards/role.guard';
import {FitnessProviderRoleGuard} from './guards/fitness-provider-role.guard';
import {FollowerComponent} from './components/follower/follower.component';
import {FitnessProvidersFollowedComponent} from './components/fitness-providers-followed/fitness-providers-followed.component';
import {CreateExerciseComponent} from './components/create-exercise/create-exercise.component';
import {CreateCourseComponent} from './components/create-course/create-course.component';
import {EditFitnessProviderProfileComponent} from './components/edit-fitness-provider-profile/edit-fitness-provider-profile.component';
import {OwnExercisesComponent} from './components/own-exercises/own-exercises.component';
import {OwnWorkoutsComponent} from './components/own-workouts/own-workouts.component';
import {OwnTrainingScheduleComponent} from './components/own-training-schedule/own-training-schedule.component';
import {ExerciseComponent} from './components/exercise/exercise.component';
import {EditExerciseComponent} from './components/edit-exercise/edit-exercise.component';
import {WorkoutComponent} from './components/workout/workout.component';
import {EditWorkoutComponent} from './components/edit-workout/edit-workout.component';
import {CreateForFitnessProviderComponent} from './components/create-for-fitness-provider/create-for-fitness-provider.component';
import {MyContentFitnessProviderComponent} from './components/my-content-fitness-provider/my-content-fitness-provider.component';
import {EditCourseComponent} from './components/edit-course/edit-course.component';
import {OwnCoursesComponent} from './components/own-courses/own-courses.component';
import {CourseComponent} from './components/course/course.component';
import {CreateWorkoutComponent} from './components/create-workout/create-workout.component';
import {WorkoutExercisesComponent} from './components/workout-exercises/workout-exercises.component';
import {CreateExercisesForWorkoutComponent} from './components/create-exercises-for-workout/create-exercises-for-workout.component';
import {EditWorkoutExercisesComponent} from './components/edit-workout-exercises/edit-workout-exercises.component';
import {CreateTrainingScheduleManuallyComponent} from './components/create-training-schedule-manually/create-training-schedule-manually.component';
import {TrainingScheduleComponent} from './components/training-schedule/training-schedule.component';
import {EditTrainingScheduleComponent} from './components/edit-training-schedule/edit-training-schedule.component';
import {FitnessProviderComponent} from './components/fitness-provider/fitness-provider.component';
import {CourseDudeViewComponent} from './components/course-dude-view/course-dude-view.component';
import {FollowerDudeComponent} from './components/follower-dude/follower-dude.component';
import {EditTrainingScheduleWorkoutsComponent} from './components/edit-training-schedule-workouts/edit-training-schedule-workouts.component';
import {StatisticTrainingscheduleComponent} from './components/statistic-trainingschedule/statistic-trainingschedule.component';

const routes: Routes = [

  {path: '', component: LoginComponent},
  {path: 'login-as-fitness-provider', component: LoginAsFitnessProviderComponent},
  {path: 'register-as-dude', component: RegisterAsDudeComponent},
  {path: 'register-as-fitness-provider', component: RegisterAsFitnessProviderComponent},
  {path: 'follower-dude', component: FollowerDudeComponent},
  {path: 'dude-profile', canActivate: [RoleGuard], component: DudeProfileComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'bros', canActivate: [RoleGuard], component: BrosComponent},
  {path: 'create', canActivate: [RoleGuard], component: CreateComponent},
  {path: 'create-for-FP', canActivate: [FitnessProviderRoleGuard], component: CreateForFitnessProviderComponent},
  {path: 'create-exercise', canActivate: [RoleGuard], component: CreateExerciseComponent},
  {path: 'create-exercise-for-workout', canActivate: [RoleGuard], component: CreateExercisesForWorkoutComponent},
  {path: 'create-course', canActivate: [FitnessProviderRoleGuard], component: CreateCourseComponent},
  {path: 'create-workout', canActivate: [RoleGuard], component: CreateWorkoutComponent},
  {path: 'create-trainingSchedule-m', canActivate: [RoleGuard], component: CreateTrainingScheduleManuallyComponent},
  {path: 'workout-exercises', canActivate: [RoleGuard], component: WorkoutExercisesComponent},
  {path: 'find', canActivate: [AuthGuard], component: FindComponent},
  {path: 'history', canActivate: [RoleGuard], component: HistoryComponent},
  {path: 'myContent', canActivate: [RoleGuard], component: MyContentComponent},
  {path: 'myContent-FP', canActivate: [FitnessProviderRoleGuard], component: MyContentFitnessProviderComponent},
  {path: 'favourites', canActivate: [RoleGuard], component: FavouritesComponent},
  {path: 'edit-dude', canActivate: [RoleGuard], component: EditProfileComponent},
  {path: 'edit-fitnessProvider', canActivate: [FitnessProviderRoleGuard], component: EditFitnessProviderProfileComponent},
  {path: 'fitnessProvider-profile', canActivate: [AuthGuard], component: FitnessProviderProfileComponent},
  {path: 'follower', canActivate: [FitnessProviderRoleGuard], component: FollowerComponent},
  {path: 'fitnessProviders', canActivate: [RoleGuard], component: FitnessProvidersFollowedComponent},
  {path: 'fitnessProvider', canActivate: [RoleGuard], component: FitnessProviderComponent},
  {path: 'myExercises', canActivate: [RoleGuard], component: OwnExercisesComponent},
  {path: 'myWorkouts', canActivate: [RoleGuard], component: OwnWorkoutsComponent},
  {path: 'myCourses', canActivate: [FitnessProviderRoleGuard], component: OwnCoursesComponent},
  {path: 'myTrainingSchedules', canActivate: [RoleGuard], component: OwnTrainingScheduleComponent},
  {path: 'exercise', canActivate: [AuthGuard], component: ExerciseComponent},
  {path: 'edit-exercise', canActivate: [RoleGuard], component: EditExerciseComponent},
  {path: 'edit-course', canActivate: [FitnessProviderRoleGuard], component: EditCourseComponent},
  {path: 'workout', canActivate: [AuthGuard], component: WorkoutComponent},
  {path: 'edit-workout', canActivate: [RoleGuard], component: EditWorkoutComponent},
  {path: 'course-dude-view', canActivate: [RoleGuard], component: CourseDudeViewComponent},
  {path: 'edit-workout-exercises', canActivate: [RoleGuard], component: EditWorkoutExercisesComponent},
  {path: 'course', canActivate: [FitnessProviderRoleGuard], component: CourseComponent},
  {path: 'trainingSchedule', canActivate: [AuthGuard], component: TrainingScheduleComponent},
  {path: 'edit-training-schedule', canActivate: [RoleGuard], component: EditTrainingScheduleComponent},
  {path: 'edit-training-workouts', canActivate: [RoleGuard], component: EditTrainingScheduleWorkoutsComponent},
  {path: 'statistics', canActivate: [RoleGuard], component: StatisticTrainingscheduleComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
