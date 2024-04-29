import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeCreationDialogComponent } from './employee-creation-dialog.component';

describe('EmployeeCreationDialogComponent', () => {
  let component: EmployeeCreationDialogComponent;
  let fixture: ComponentFixture<EmployeeCreationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeCreationDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeCreationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
