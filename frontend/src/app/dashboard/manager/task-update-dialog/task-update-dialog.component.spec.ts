import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskUpdateDialogComponent } from './task-update-dialog.component';

describe('TaskUpdateDialogComponent', () => {
  let component: TaskUpdateDialogComponent;
  let fixture: ComponentFixture<TaskUpdateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TaskUpdateDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TaskUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
