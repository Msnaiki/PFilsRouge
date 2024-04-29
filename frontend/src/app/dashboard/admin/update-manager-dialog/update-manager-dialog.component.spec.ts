import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateManagerDialogComponent } from './update-manager-dialog.component';

describe('UpdateManagerDialogComponent', () => {
  let component: UpdateManagerDialogComponent;
  let fixture: ComponentFixture<UpdateManagerDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateManagerDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateManagerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
