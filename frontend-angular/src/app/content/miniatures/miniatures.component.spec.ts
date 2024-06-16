import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiniaturesComponent } from './miniatures.component';

describe('MiniaturesComponent', () => {
  let component: MiniaturesComponent;
  let fixture: ComponentFixture<MiniaturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MiniaturesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MiniaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
