import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateEmployeeDialogComponent } from './update-employee-dialog.component';

describe('UpdateEmployeeDialogComponent', () => {
  let component: UpdateEmployeeDialogComponent;
  let fixture: ComponentFixture<UpdateEmployeeDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateEmployeeDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateEmployeeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
