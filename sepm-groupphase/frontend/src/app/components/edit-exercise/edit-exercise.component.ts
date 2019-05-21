import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-edit-exercise',
  templateUrl: './edit-exercise.component.html',
  styleUrls: ['./edit-exercise.component.scss']
})
export class EditExerciseComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  editExercise(){
    this.router.navigate(['/myExercises']);
  }

}
